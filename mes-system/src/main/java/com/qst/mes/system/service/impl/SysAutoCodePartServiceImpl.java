package com.qst.mes.system.service.impl;

import com.qst.mes.common.constant.UserConstants;
import com.qst.mes.common.core.domain.entity.SysAutoCodePart;
import com.qst.mes.common.utils.StringUtils;
import com.qst.mes.system.mapper.SysAutoCodePartMapper;
import com.qst.mes.system.service.IAutoCodePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAutoCodePartServiceImpl implements IAutoCodePartService {

    @Autowired
    private SysAutoCodePartMapper sysAutoCodePartMapper;

    @Override
    public List<SysAutoCodePart> listPart(SysAutoCodePart sysAutoCodePart) {
        return sysAutoCodePartMapper.selectSysAutoCodePartList(sysAutoCodePart);
    }

    @Override
    public SysAutoCodePart findById(Long partId) {
        return sysAutoCodePartMapper.findById(partId);
    }

    @Override
    public String checkPartUnique(SysAutoCodePart sysAutoCodePart) {
        SysAutoCodePart part = sysAutoCodePartMapper.checkPartUnique(sysAutoCodePart);
        Long partId = StringUtils.isNull(sysAutoCodePart.getPartId())?-1L:sysAutoCodePart.getPartId();
        if(StringUtils.isNotNull(part) && partId.longValue() != part.getPartId().longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertPart(SysAutoCodePart sysAutoCodePart) {
        return sysAutoCodePartMapper.add(sysAutoCodePart);
    }

    @Override
    public int updatePart(SysAutoCodePart sysAutoCodePart) {
        return sysAutoCodePartMapper.updateSysAutoCodePart(sysAutoCodePart);
    }

    @Override
    public int deleteByIds(Long[] partIds) {
        for (Long partId:partIds
             ) {
            sysAutoCodePartMapper.deleteById(partId);
        }
        return 1;
    }


}
