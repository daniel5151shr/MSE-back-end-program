package com.qst.mes.core.qc.service.impl;

import java.util.List;

import com.qst.mes.common.constant.UserConstants;
import com.qst.mes.common.utils.DateUtils;
import com.qst.mes.common.utils.StringUtils;
import com.qst.mes.core.qc.service.IQcIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qst.mes.core.qc.mapper.QcIndexMapper;
import com.qst.mes.core.qc.domain.QcIndex;

/**
 * 检测项Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-05-17
 */
@Service
public class QcIndexServiceImpl implements IQcIndexService
{
    @Autowired
    private QcIndexMapper qcIndexMapper;

    /**
     * 查询检测项
     * 
     * @param indexId 检测项主键
     * @return 检测项
     */
    @Override
    public QcIndex selectQcIndexByIndexId(Long indexId)
    {
        return qcIndexMapper.selectQcIndexByIndexId(indexId);
    }

    /**
     * 查询检测项列表
     * 
     * @param qcIndex 检测项
     * @return 检测项
     */
    @Override
    public List<QcIndex> selectQcIndexList(QcIndex qcIndex)
    {
        return qcIndexMapper.selectQcIndexList(qcIndex);
    }

    @Override
    public String checkIndexCodeUnique(QcIndex qcIndex) {
        QcIndex index = qcIndexMapper.checkIndexCodeUnique(qcIndex);
        Long indexId = qcIndex.getIndexId()==null?-1L:qcIndex.getIndexId();
        if(StringUtils.isNotNull(index) &&index.getIndexId().longValue() != indexId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkIndexNameUnique(QcIndex qcIndex) {
        QcIndex index = qcIndexMapper.checkIndexNameUnique(qcIndex);
        Long indexId = qcIndex.getIndexId()==null?-1L:qcIndex.getIndexId();
        if(StringUtils.isNotNull(index) &&index.getIndexId().longValue() != indexId.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增检测项
     * 
     * @param qcIndex 检测项
     * @return 结果
     */
    @Override
    public int insertQcIndex(QcIndex qcIndex)
    {
        qcIndex.setCreateTime(DateUtils.getNowDate());
        return qcIndexMapper.insertQcIndex(qcIndex);
    }

    /**
     * 修改检测项
     * 
     * @param qcIndex 检测项
     * @return 结果
     */
    @Override
    public int updateQcIndex(QcIndex qcIndex)
    {
        qcIndex.setUpdateTime(DateUtils.getNowDate());
        return qcIndexMapper.updateQcIndex(qcIndex);
    }

    /**
     * 批量删除检测项
     * 
     * @param indexIds 需要删除的检测项主键
     * @return 结果
     */
    @Override
    public int deleteQcIndexByIndexIds(Long[] indexIds)
    {
        return qcIndexMapper.deleteQcIndexByIndexIds(indexIds);
    }

    /**
     * 删除检测项信息
     * 
     * @param indexId 检测项主键
     * @return 结果
     */
    @Override
    public int deleteQcIndexByIndexId(Long indexId)
    {
        return qcIndexMapper.deleteQcIndexByIndexId(indexId);
    }
}
