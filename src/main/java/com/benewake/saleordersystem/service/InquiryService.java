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
    List<Inquiry> selectInquiryList(List<FilterCriteria> filters);
}
