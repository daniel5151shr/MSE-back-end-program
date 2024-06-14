package com.qst.mes.core.wm.service.impl;

import java.util.List;
import com.qst.mes.common.utils.DateUtils;
import com.qst.mes.core.wm.service.IWmProductRecptLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qst.mes.core.wm.mapper.WmProductRecptLineMapper;
import com.qst.mes.core.wm.domain.WmProductRecptLine;

/**
 * 产品入库记录行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-09-22
 */
@Service
public class WmProductRecptLineServiceImpl implements IWmProductRecptLineService
{
    @Autowired
    private WmProductRecptLineMapper wmProductRecptLineMapper;

    /**
     * 查询产品入库记录行
     * 
     * @param lineId 产品入库记录行主键
     * @return 产品入库记录行
     */
    @Override
    public WmProductRecptLine selectWmProductRecptLineByLineId(Long lineId)
    {
        return wmProductRecptLineMapper.selectWmProductRecptLineByLineId(lineId);
    }

    /**
     * 查询产品入库记录行列表
     * 
     * @param wmProductRecptLine 产品入库记录行
     * @return 产品入库记录行
     */
    @Override
    public List<WmProductRecptLine> selectWmProductRecptLineList(WmProductRecptLine wmProductRecptLine)
    {
        return wmProductRecptLineMapper.selectWmProductRecptLineList(wmProductRecptLine);
    }

    /**
     * 新增产品入库记录行
     * 
     * @param wmProductRecptLine 产品入库记录行
     * @return 结果
     */
    @Override
    public int insertWmProductRecptLine(WmProductRecptLine wmProductRecptLine)
    {
        wmProductRecptLine.setCreateTime(DateUtils.getNowDate());
        return wmProductRecptLineMapper.insertWmProductRecptLine(wmProductRecptLine);
    }

    /**
     * 修改产品入库记录行
     * 
     * @param wmProductRecptLine 产品入库记录行
     * @return 结果
     */
    @Override
    public int updateWmProductRecptLine(WmProductRecptLine wmProductRecptLine)
    {
        wmProductRecptLine.setUpdateTime(DateUtils.getNowDate());
        return wmProductRecptLineMapper.updateWmProductRecptLine(wmProductRecptLine);
    }

    /**
     * 批量删除产品入库记录行
     * 
     * @param lineIds 需要删除的产品入库记录行主键
     * @return 结果
     */
    @Override
    public int deleteWmProductRecptLineByLineIds(Long[] lineIds)
    {
        return wmProductRecptLineMapper.deleteWmProductRecptLineByLineIds(lineIds);
    }

    /**
     * 删除产品入库记录行信息
     * 
     * @param lineId 产品入库记录行主键
     * @return 结果
     */
    @Override
    public int deleteWmProductRecptLineByLineId(Long lineId)
    {
        return wmProductRecptLineMapper.deleteWmProductRecptLineByLineId(lineId);
    }

    @Override
    public int deleteByRecptId(Long recptId) {
        return wmProductRecptLineMapper.deleteByRecptId(recptId);
    }
}
