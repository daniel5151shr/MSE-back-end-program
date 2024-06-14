package com.qst.mes.core.qc.service.impl;

import com.qst.mes.core.qc.domain.QcPendingInspect;
import com.qst.mes.core.qc.mapper.QcPendingInspectMapper;
import com.qst.mes.core.qc.service.IQcPendingInspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QcPendingInspectServiceImpl implements IQcPendingInspectService {

    @Autowired
    private QcPendingInspectMapper qcPendingInspectMapper;

    @Override
    public List<QcPendingInspect> selectQcPendingList(QcPendingInspect qcPendingInspect) {
        return qcPendingInspectMapper.selectQcPendingList(qcPendingInspect);
    }
}
