package com.qst.mes.core.md.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.qst.mes.common.constant.UserConstants;
import com.qst.mes.core.tm.domain.TmToolType;
import com.qst.mes.core.tm.service.ITmToolTypeService;
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
import com.qst.mes.core.md.domain.MdWorkstationTool;
import com.qst.mes.core.md.service.IMdWorkstationToolService;
import com.qst.mes.common.utils.poi.ExcelUtil;
import com.qst.mes.common.core.page.TableDataInfo;

/**
 * 工装夹具资源Controller
 * 
 * @author yinjinlu
 * @date 2022-05-12
 */
@RestController
@RequestMapping("/mes/md/workstationtool")
public class MdWorkstationToolController extends BaseController
{
    @Autowired
    private IMdWorkstationToolService mdWorkstationToolService;

    @Autowired
    private ITmToolTypeService toolTypeService;

    /**
     * 查询工装夹具资源列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MdWorkstationTool mdWorkstationTool)
    {
        startPage();
        List<MdWorkstationTool> list = mdWorkstationToolService.selectMdWorkstationToolList(mdWorkstationTool);
        return getDataTable(list);
    }

    /**
     * 导出工装夹具资源列表
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:export')")
    @Log(title = "工装夹具资源", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MdWorkstationTool mdWorkstationTool)
    {
        List<MdWorkstationTool> list = mdWorkstationToolService.selectMdWorkstationToolList(mdWorkstationTool);
        ExcelUtil<MdWorkstationTool> util = new ExcelUtil<MdWorkstationTool>(MdWorkstationTool.class);
        util.exportExcel(response, list, "工装夹具资源数据");
    }

    /**
     * 获取工装夹具资源详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(mdWorkstationToolService.selectMdWorkstationToolByRecordId(recordId));
    }

    /**
     * 新增工装夹具资源
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:add')")
    @Log(title = "工装夹具资源", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MdWorkstationTool mdWorkstationTool)
    {
        if(UserConstants.NOT_UNIQUE.equals(mdWorkstationToolService.checkToolTypeExists(mdWorkstationTool))){
            return AjaxResult.error("此工装夹具类型已添加！");
        }
        TmToolType type = toolTypeService.selectTmToolTypeByToolTypeId(mdWorkstationTool.getToolTypeId());
        mdWorkstationTool.setToolTypeCode(type.getToolTypeCode());
        mdWorkstationTool.setToolTypeName(type.getToolTypeName());
        return toAjax(mdWorkstationToolService.insertMdWorkstationTool(mdWorkstationTool));
    }

    /**
     * 修改工装夹具资源
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:edit')")
    @Log(title = "工装夹具资源", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MdWorkstationTool mdWorkstationTool)
    {
        if(UserConstants.NOT_UNIQUE.equals(mdWorkstationToolService.checkToolTypeExists(mdWorkstationTool))){
            return AjaxResult.error("此工装夹具类型已添加！");
        }
        TmToolType type = toolTypeService.selectTmToolTypeByToolTypeId(mdWorkstationTool.getToolTypeId());
        mdWorkstationTool.setToolTypeCode(type.getToolTypeCode());
        mdWorkstationTool.setToolTypeName(type.getToolTypeName());
        return toAjax(mdWorkstationToolService.updateMdWorkstationTool(mdWorkstationTool));
    }

    /**
     * 删除工装夹具资源
     */
    @PreAuthorize("@ss.hasPermi('mes:md:workstation:remove')")
    @Log(title = "工装夹具资源", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(mdWorkstationToolService.deleteMdWorkstationToolByRecordIds(recordIds));
    }
}
