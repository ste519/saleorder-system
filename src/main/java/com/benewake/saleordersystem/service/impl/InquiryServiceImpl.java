package com.benewake.saleordersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benewake.saleordersystem.entity.FilterCriteria;
import com.benewake.saleordersystem.entity.Inquiry;
import com.benewake.saleordersystem.mapper.InquiryMapper;
import com.benewake.saleordersystem.service.InquiryService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import com.benewake.saleordersystem.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月05 16:19
 * 描 述： TODO
 */
@Service
public class InquiryServiceImpl implements InquiryService, BenewakeConstants {

    @Autowired
    private InquiryMapper inquiryMapper;
    private List<Inquiry> getAll() {
        return inquiryMapper.selectList(null);
    }
    @Override
    public List<Inquiry> selectInquiryList(List<FilterCriteria> filters) {
        // 参数为空 获取全部数据
        if(filters == null) {
            return getAll();
        }
        // 添加筛选条件
        QueryWrapper<Inquiry> queryWrapper = new QueryWrapper<>();
        CommonUtils.addFilters(filters, queryWrapper);

        return inquiryMapper.selectList(queryWrapper);
    }
}
