package com.qst.mes.core.wm.service.impl;

import java.util.List;

import com.qst.mes.common.constant.UserConstants;
import com.qst.mes.common.utils.DateUtils;
import com.qst.mes.common.utils.StringUtils;
import com.qst.mes.core.wm.domain.tx.ProductRecptTxBean;
import com.qst.mes.core.wm.service.IWmProductRecptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qst.mes.core.wm.mapper.WmProductRecptMapper;
import com.qst.mes.core.wm.domain.WmProductRecpt;

/**
 * 产品入库录Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-09-22
 */
@Service
public class WmProductRecptServiceImpl implements IWmProductRecptService
{
    @Autowired
    private WmProductRecptMapper wmProductRecptMapper;

    /**
     * 查询产品入库录
     * 
     * @param recptId 产品入库录主键
     * @return 产品入库录
     */
    @Override
    public WmProductRecpt selectWmProductRecptByRecptId(Long recptId)
    {
        return wmProductRecptMapper.selectWmProductRecptByRecptId(recptId);
    }

    /**
     * 查询产品入库录列表
     * 
     * @param wmProductRecpt 产品入库录
     * @return 产品入库录
     */
    @Override
    public List<WmProductRecpt> selectWmProductRecptList(WmProductRecpt wmProductRecpt)
    {
        return wmProductRecptMapper.selectWmProductRecptList(wmProductRecpt);
    }

    @Override
    public String checkUnique(WmProductRecpt wmProductRecpt) {
        WmProductRecpt recpt = wmProductRecptMapper.checkUnique(wmProductRecpt);
        Long recptId = wmProductRecpt.getRecptId() ==null? -1L:wmProductRecpt.getRecptId();
        if(StringUtils.isNotNull(recpt) && recpt.getRecptId().longValue() != recptId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增产品入库录
     * 
     * @param wmProductRecpt 产品入库录
     * @return 结果
     */
    @Override
    public int insertWmProductRecpt(WmProductRecpt wmProductRecpt)
    {
        wmProductRecpt.setCreateTime(DateUtils.getNowDate());
        return wmProductRecptMapper.insertWmProductRecpt(wmProductRecpt);
    }

    /**
     * 修改产品入库录
     * 
     * @param wmProductRecpt 产品入库录
     * @return 结果
     */
    @Override
    public int updateWmProductRecpt(WmProductRecpt wmProductRecpt)
    {
        wmProductRecpt.setUpdateTime(DateUtils.getNowDate());
        return wmProductRecptMapper.updateWmProductRecpt(wmProductRecpt);
    }

    /**
     * 批量删除产品入库录
     * 
     * @param recptIds 需要删除的产品入库录主键
     * @return 结果
     */
    @Override
    public int deleteWmProductRecptByRecptIds(Long[] recptIds)
    {
        return wmProductRecptMapper.deleteWmProductRecptByRecptIds(recptIds);
    }

    /**
     * 删除产品入库录信息
     * 
     * @param recptId 产品入库录主键
     * @return 结果
     */
    @Override
    public int deleteWmProductRecptByRecptId(Long recptId)
    {
        return wmProductRecptMapper.deleteWmProductRecptByRecptId(recptId);
    }

    @Override
    public List<ProductRecptTxBean> getTxBean(Long recptId) {
        return wmProductRecptMapper.getTxBean(recptId);
    }


}
