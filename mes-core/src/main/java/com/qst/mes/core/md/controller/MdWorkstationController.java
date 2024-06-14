package com.qst.mes.core.md.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.qst.mes.common.constant.UserConstants;
import com.qst.mes.common.utils.StringUtils;
import com.qst.mes.core.md.domain.MdWorkshop;
import com.qst.mes.core.md.service.*;
import com.qst.mes.core.md.service.*;
import com.qst.mes.core.pro.domain.ProProcess;
import com.qst.mes.core.pro.service.IProProcessService;
import com.qst.mes.core.wm.domain.WmStorageArea;
import com.qst.mes.core.wm.domain.WmStorageLocation;
import com.qst.mes.core.wm.domain.WmWarehouse;
import com.qst.mes.core.wm.service.IWmStorageAreaService;
import com.qst.mes.core.wm.service.IWmStorageLocationService;
import com.qst.mes.core.wm.service.IWmWarehouseService;
import com.qst.mes.core.wm.utils.WmBarCodeUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qst.mes.common.annotation.Log;
import com.qst.mes.common.core.controller.BaseController;
import com.qst.mes.common.core.domain.AjaxResult;
import com.qst.mes.common.enums.BusinessType;
import com.qst.mes.core.md.domain.MdWorkstation;
import com.qst.mes.common.utils.poi.ExcelUtil;
import com.qst.mes.common.core.page.TableDataInfo;

/**
 * 工作站Controller
 * 
 * @author yinjinlu
 * @date 2022-05-10
 */
@RestController
@RequestMapping("/mes/md/workstation")
public class MdWorkstationController extends BaseController
{
    @Autowired
    private IMdWorkstationService mdWorkstationService;

    @Autowired
    private IMdWorkstationMachineService mdWorkstationMachineService;

    @Autowired
    private IMdWorkstationToolService mdWorkstationToolService;

    @Autowired
    private IMdWorkstationWorkerService mdWorkstationWorkerService;

    @Autowired
    private IProProcessService proProcessService;

    @Autowired
    private IMdWorkshopService mdWorkshopService;

    @Autowired
    private IWmWarehouseService wmWarehouseService;

    @Autowired
    private IWmStorageLocationService wmStorageLocationService;

    @Autowired
    private IWmStorageAreaService wmStorageAreaService;

    @Autowired
    private WmBarCodeUtil barCodeUtil;

    /**
     * 查询工作站列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MdWorkstation mdWorkstation)
    {
        startPage();
        List<MdWorkstation> list = mdWorkstationService.selectMdWorkstationList(mdWorkstation);
        return getDataTable(list);
    }

    /**
     * 导出工作站列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:export')")
    @Log(title = "工作站", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdWorkstation mdWorkstation)
    {
        List<MdWorkstation> list = mdWorkstationService.selectMdWorkstationList(mdWorkstation);
        ExcelUtil<MdWorkstation> util = new ExcelUtil<MdWorkstation>(MdWorkstation.class);
        util.exportExcel(response, list, "工作站数据");
    }

    /**
     * 获取工作站详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:query')")
    @GetMapping(value = "/{workstationId}")
    public AjaxResult getInfo(@PathVariable("workstationId") Long workstationId)
    {
        return AjaxResult.success(mdWorkstationService.selectMdWorkstationByWorkstationId(workstationId));
    }

    /**
     * 新增工作站
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:add')")
    @Log(title = "工作站", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkstation mdWorkstation)
    {
        if(UserConstants.NOT_UNIQUE.equals(mdWorkstationService.checkWorkStationCodeUnique(mdWorkstation))){
            return AjaxResult.error("工作站编码已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(mdWorkstationService.checkWorkStationNameUnique(mdWorkstation))){
            return AjaxResult.error("工作站名称已存在！");
        }
        ProProcess process = proProcessService.selectProProcessByProcessId(mdWorkstation.getProcessId());
        mdWorkstation.setProcessCode(process.getProcessCode());
        mdWorkstation.setProcessName(process.getProcessName());

        MdWorkshop workshop = mdWorkshopService.selectMdWorkshopByWorkshopId(mdWorkstation.getWorkshopId());
        mdWorkstation.setWorkshopCode(workshop.getWorkshopCode());
        mdWorkstation.setWorkshopName(workshop.getWorkshopName());

        //线边库的设置
        WmWarehouse warehouse = null;
        WmStorageLocation location = null;
        WmStorageArea area = null;
        if(StringUtils.isNotNull(mdWorkstation.getWarehouseId())){
            //如果有指定线边库
            warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(mdWorkstation.getWarehouseId());
            location = wmStorageLocationService.selectWmStorageLocationByLocationId(mdWorkstation.getLocationId());
            area = wmStorageAreaService.selectWmStorageAreaByAreaId(mdWorkstation.getAreaId());
        }else {
            //设置默认的线边库
            warehouse = wmWarehouseService.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
            if(StringUtils.isNull(warehouse)){
                //如果没有找到默认的线边库，则进行一次初始化
                warehouse = wmWarehouseService.initVirtualWarehouse();
            }
            location = wmStorageLocationService.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
            area = wmStorageAreaService.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);
        }
        mdWorkstation.setWarehouseId(warehouse.getWarehouseId());
        mdWorkstation.setWarehouseCode(warehouse.getWarehouseCode());
        mdWorkstation.setWarehouseName(warehouse.getWarehouseName());
        mdWorkstation.setLocationId(location.getLocationId());
        mdWorkstation.setLocationCode(location.getLocationCode());
        mdWorkstation.setLocationName(location.getLocationName());
        mdWorkstation.setAreaId(area.getAreaId());
        mdWorkstation.setAreaCode(area.getAreaCode());
        mdWorkstation.setAreaName(area.getAreaName());
        mdWorkstationService.insertMdWorkstation(mdWorkstation);
        barCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_WORKSTATION,mdWorkstation.getWorkstationId(), mdWorkstation.getWorkstationCode(),mdWorkstation.getWorkstationName());
        return AjaxResult.success(mdWorkstation.getWorkstationId());
    }

    /**
     * 修改工作站
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:edit')")
    @Log(title = "工作站", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkstation mdWorkstation)
    {
        if(UserConstants.NOT_UNIQUE.equals(mdWorkstationService.checkWorkStationCodeUnique(mdWorkstation))){
            return AjaxResult.error("工作站编码已存在！");
        }
        if(UserConstants.NOT_UNIQUE.equals(mdWorkstationService.checkWorkStationNameUnique(mdWorkstation))){
            return AjaxResult.error("工作站名称已存在！");
        }
        ProProcess process = proProcessService.selectProProcessByProcessId(mdWorkstation.getProcessId());
        if(!StringUtils.isNotNull(process)){
            return AjaxResult.error("工序不存在！");
        }
        mdWorkstation.setProcessCode(process.getProcessCode());
        mdWorkstation.setProcessName(process.getProcessName());

        MdWorkshop workshop = mdWorkshopService.selectMdWorkshopByWorkshopId(mdWorkstation.getWorkshopId());
        if(!StringUtils.isNotNull(workshop)){
            return AjaxResult.error("车间不存在！");
        }
        mdWorkstation.setWorkshopCode(workshop.getWorkshopCode());
        mdWorkstation.setWorkshopName(workshop.getWorkshopName());

        //线边库的设置
        WmWarehouse warehouse = null;
        WmStorageLocation location = null;
        WmStorageArea area = null;
        if(StringUtils.isNotNull(mdWorkstation.getWarehouseId())){
            //如果有指定线边库
            warehouse = wmWarehouseService.selectWmWarehouseByWarehouseId(mdWorkstation.getWarehouseId());
            location = wmStorageLocationService.selectWmStorageLocationByLocationId(mdWorkstation.getLocationId());
            area = wmStorageAreaService.selectWmStorageAreaByAreaId(mdWorkstation.getAreaId());
        }else {
            //设置默认的线边库
            warehouse = wmWarehouseService.selectWmWarehouseByWarehouseCode(UserConstants.VIRTUAL_WH);
            if(StringUtils.isNull(warehouse)){
                //如果没有找到默认的线边库，则进行一次初始化
                warehouse = wmWarehouseService.initVirtualWarehouse();
            }
            location = wmStorageLocationService.selectWmStorageLocationByLocationCode(UserConstants.VIRTUAL_WS);
            area = wmStorageAreaService.selectWmStorageAreaByAreaCode(UserConstants.VIRTUAL_WA);
        }
        mdWorkstation.setWarehouseCode(warehouse.getWarehouseCode());
        mdWorkstation.setWarehouseName(warehouse.getWarehouseName());
        mdWorkstation.setLocationCode(location.getLocationCode());
        mdWorkstation.setLocationName(location.getLocationName());
        mdWorkstation.setAreaCode(area.getAreaCode());
        mdWorkstation.setAreaName(area.getAreaName());

        return toAjax(mdWorkstationService.updateMdWorkstation(mdWorkstation));
    }

    /**
     * 删除工作站
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:remove')")
    @Log(title = "工作站", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{workstationIds}")
    public AjaxResult remove(@PathVariable Long[] workstationIds)
    {
        for (Long workstationId: workstationIds
             ) {
            mdWorkstationMachineService.deleteByWorkstationId(workstationId);
            mdWorkstationToolService.deleteByWorkstationId(workstationId);
            mdWorkstationWorkerService.deleteByWorkstationId(workstationId);
        }
        return toAjax(mdWorkstationService.deleteMdWorkstationByWorkstationIds(workstationIds));
    }
}
