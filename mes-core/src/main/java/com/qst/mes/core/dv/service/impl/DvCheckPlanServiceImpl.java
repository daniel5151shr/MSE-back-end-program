package com.qst.mes.core.dv.service.impl;

import java.util.List;

import com.qst.mes.common.constant.UserConstants;
import com.qst.mes.common.utils.DateUtils;
import com.qst.mes.common.utils.StringUtils;
import com.qst.mes.core.dv.service.IDvCheckPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qst.mes.core.dv.mapper.DvCheckPlanMapper;
import com.qst.mes.core.dv.domain.DvCheckPlan;

/**
 * 设备点检计划头Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-06-16
 */
@Service
public class DvCheckPlanServiceImpl implements IDvCheckPlanService
{
    @Autowired
    private DvCheckPlanMapper dvCheckPlanMapper;

    /**
     * 查询设备点检计划头
     * 
     * @param planId 设备点检计划头主键
     * @return 设备点检计划头
     */
    @Override
    public DvCheckPlan selectDvCheckPlanByPlanId(Long planId)
    {
        return dvCheckPlanMapper.selectDvCheckPlanByPlanId(planId);
    }

    /**
     * 查询设备点检计划头列表
     * 
     * @param dvCheckPlan 设备点检计划头
     * @return 设备点检计划头
     */
    @Override
    public List<DvCheckPlan> selectDvCheckPlanList(DvCheckPlan dvCheckPlan)
    {
        return dvCheckPlanMapper.selectDvCheckPlanList(dvCheckPlan);
    }

    @Override
    public String checkPlanCodeUnique(DvCheckPlan dvCheckPlan) {
        DvCheckPlan plan = dvCheckPlanMapper.checkPlanCodeUnique(dvCheckPlan);
        Long planId = dvCheckPlan.getPlanId()==null?-1L:dvCheckPlan.getPlanId();
        if(StringUtils.isNotNull(plan) && plan.getPlanId().longValue()==planId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增设备点检计划头
     * 
     * @param dvCheckPlan 设备点检计划头
     * @return 结果
     */
    @Override
    public int insertDvCheckPlan(DvCheckPlan dvCheckPlan)
    {
        dvCheckPlan.setCreateTime(DateUtils.getNowDate());
        return dvCheckPlanMapper.insertDvCheckPlan(dvCheckPlan);
    }

    /**
     * 修改设备点检计划头
     * 
     * @param dvCheckPlan 设备点检计划头
     * @return 结果
     */
    @Override
    public int updateDvCheckPlan(DvCheckPlan dvCheckPlan)
    {
        dvCheckPlan.setUpdateTime(DateUtils.getNowDate());
        return dvCheckPlanMapper.updateDvCheckPlan(dvCheckPlan);
    }

    /**
     * 批量删除设备点检计划头
     * 
     * @param planIds 需要删除的设备点检计划头主键
     * @return 结果
     */
    @Override
    public int deleteDvCheckPlanByPlanIds(Long[] planIds)
    {
        return dvCheckPlanMapper.deleteDvCheckPlanByPlanIds(planIds);
    }

    /**
     * 删除设备点检计划头信息
     * 
     * @param planId 设备点检计划头主键
     * @return 结果
     */
    @Override
    public int deleteDvCheckPlanByPlanId(Long planId)
    {
        return dvCheckPlanMapper.deleteDvCheckPlanByPlanId(planId);
    }
}
