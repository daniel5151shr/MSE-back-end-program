package com.qst.mes.core.qc.controller;

import com.qst.mes.common.core.controller.BaseController;
import com.qst.mes.common.core.page.TableDataInfo;
import com.qst.mes.core.qc.domain.QcPendingInspect;
import com.qst.mes.core.qc.service.IQcPendingInspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mes/qc/pending")
public class QcPendingInspectController extends BaseController {

    @Autowired
    private IQcPendingInspectService qcPendingInspectService;


    /**
     * 获取待检验任务
     */
    @PreAuthorize("@ss.hasPermi('mes:qc:pending:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcPendingInspect qcPendingInspect)
    {
        startPage();
        List<QcPendingInspect> list = qcPendingInspectService.selectQcPendingList(qcPendingInspect);
        return getDataTable(list);
    }
}
