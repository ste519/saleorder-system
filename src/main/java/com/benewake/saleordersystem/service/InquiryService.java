package com.benewake.saleordersystem.service;

import com.benewake.saleordersystem.entity.FilterCriteria;
import com.benewake.saleordersystem.entity.Inquiry;

import java.util.List;
import java.util.Map;

/**
 * 订单应用接口
 * @author Lcs
 */
public interface InquiryService {
    /**
     * 根据条件筛选获得数据 若果filters==null 返回全部数据
     * @param filters
     * @return
     */
    List<Map<String,Object>> selectSalesOrderVoList(List<FilterCriteria> filters,int offset,int limit);


    List<Map<String,Object>> testItemFilter(List<FilterCriteria> filters,int offset,int limit);

    /**
     * 获取展示的列名信息
     * @param tableId
     * @param planId
     * @param userId
     * @return
     */
    List<Map<String,Object>> getColMaps(Long tableId,Long planId,Long userId);
}
