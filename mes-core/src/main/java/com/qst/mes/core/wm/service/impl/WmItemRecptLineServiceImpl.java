package com.qst.mes.core.wm.service.impl;

import java.util.List;
import com.qst.mes.common.utils.DateUtils;
import com.qst.mes.core.wm.service.IWmItemRecptLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qst.mes.core.wm.mapper.WmItemRecptLineMapper;
import com.qst.mes.core.wm.domain.WmItemRecptLine;

/**
 * 物料入库单行Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-22
 */
@Service
public class WmItemRecptLineServiceImpl implements IWmItemRecptLineService
{
    @Autowired
    private WmItemRecptLineMapper wmItemRecptLineMapper;

    /**
     * 查询物料入库单行
     * 
     * @param lineId 物料入库单行主键
     * @return 物料入库单行
     */
    @Override
    public WmItemRecptLine selectWmItemRecptLineByLineId(Long lineId)
    {
        return wmItemRecptLineMapper.selectWmItemRecptLineByLineId(lineId);
    }

    /**
     * 查询物料入库单行列表
     * 
     * @param wmItemRecptLine 物料入库单行
     * @return 物料入库单行
     */
    @Override
    public List<WmItemRecptLine> selectWmItemRecptLineList(WmItemRecptLine wmItemRecptLine)
    {
        return wmItemRecptLineMapper.selectWmItemRecptLineList(wmItemRecptLine);
    }

    /**
     * 新增物料入库单行
     * 
     * @param wmItemRecptLine 物料入库单行
     * @return 结果
     */
    @Override
    public int insertWmItemRecptLine(WmItemRecptLine wmItemRecptLine)
    {
        wmItemRecptLine.setCreateTime(DateUtils.getNowDate());
        return wmItemRecptLineMapper.insertWmItemRecptLine(wmItemRecptLine);
    }

    /**
     * 修改物料入库单行
     * 
     * @param wmItemRecptLine 物料入库单行
     * @return 结果
     */
    @Override
    public int updateWmItemRecptLine(WmItemRecptLine wmItemRecptLine)
    {
        wmItemRecptLine.setUpdateTime(DateUtils.getNowDate());
        return wmItemRecptLineMapper.updateWmItemRecptLine(wmItemRecptLine);
    }

    /**
     * 批量删除物料入库单行
     * 
     * @param lineIds 需要删除的物料入库单行主键
     * @return 结果
     */
    @Override
    public int deleteWmItemRecptLineByLineIds(Long[] lineIds)
    {
        return wmItemRecptLineMapper.deleteWmItemRecptLineByLineIds(lineIds);
    }

    /**
     * 删除物料入库单行信息
     * 
     * @param lineId 物料入库单行主键
     * @return 结果
     */
    @Override
    public int deleteWmItemRecptLineByLineId(Long lineId)
    {
        return wmItemRecptLineMapper.deleteWmItemRecptLineByLineId(lineId);
    }

    @Override
    public int deleteByRecptId(Long recptId) {
        return wmItemRecptLineMapper.deleteByRecptId(recptId);
    }
}
