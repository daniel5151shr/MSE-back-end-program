package com.qst.mes.core.dv.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.qst.mes.core.dv.domain.DvRepairLine;
import com.qst.mes.core.dv.service.IDvRepairLineService;
import com.qst.mes.common.utils.poi.ExcelUtil;
import com.qst.mes.common.core.page.TableDataInfo;

/**
 * 设备维修单行Controller
 * 
 * @author yinjinlu
 * @date 2022-08-08
 */
@RestController
@RequestMapping("/mes/dv/repairline")
public class DvRepairLineController extends BaseController
{
    @Autowired
    private IDvRepairLineService dvRepairLineService;

    /**
     * 查询设备维修单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:list')")
    @GetMapping("/list")
    public TableDataInfo list(DvRepairLine dvRepairLine)
    {
        startPage();
        List<DvRepairLine> list = dvRepairLineService.selectDvRepairLineList(dvRepairLine);
        return getDataTable(list);
    }

    /**
     * 导出设备维修单行列表
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:export')")
    @Log(title = "设备维修单行", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DvRepairLine dvRepairLine)
    {
        List<DvRepairLine> list = dvRepairLineService.selectDvRepairLineList(dvRepairLine);
        ExcelUtil<DvRepairLine> util = new ExcelUtil<DvRepairLine>(DvRepairLine.class);
        util.exportExcel(response, list, "设备维修单行数据");
    }

    /**
     * 获取设备维修单行详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:query')")
    @GetMapping(value = "/{lineId}")
    public AjaxResult getInfo(@PathVariable("lineId") Long lineId)
    {
        return AjaxResult.success(dvRepairLineService.selectDvRepairLineByLineId(lineId));
    }

    /**
     * 新增设备维修单行
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:add')")
    @Log(title = "设备维修单行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DvRepairLine dvRepairLine)
    {
        return toAjax(dvRepairLineService.insertDvRepairLine(dvRepairLine));
    }

    /**
     * 修改设备维修单行
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:edit')")
    @Log(title = "设备维修单行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DvRepairLine dvRepairLine)
    {
        return toAjax(dvRepairLineService.updateDvRepairLine(dvRepairLine));
    }

    /**
     * 删除设备维修单行
     */
    @PreAuthorize("@ss.hasPermi('mes:dv:repair:remove')")
    @Log(title = "设备维修单行", businessType = BusinessType.DELETE)
	@DeleteMapping("/{lineIds}")
    public AjaxResult remove(@PathVariable Long[] lineIds)
    {
        return toAjax(dvRepairLineService.deleteDvRepairLineByLineIds(lineIds));
    }
}
