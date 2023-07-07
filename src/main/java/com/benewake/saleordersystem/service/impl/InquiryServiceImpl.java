package com.benewake.saleordersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benewake.saleordersystem.entity.FilterCriteria;
import com.benewake.saleordersystem.entity.Inquiry;
import com.benewake.saleordersystem.mapper.InquiryMapper;
import com.benewake.saleordersystem.mapper.Vo.SalesOrderVoMapper;
import com.benewake.saleordersystem.service.InquiryService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import com.benewake.saleordersystem.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private SalesOrderVoMapper salesOrderVoMapper;
    @Override
    public List<Map<String,Object>> selectSalesOrderVoList(List<FilterCriteria> filters,int offset,int limit) {

        // 添加筛选条件
        Map<String,Integer> map = new HashMap<>();
        for(int i=0;i<filters.size();++i){
            map.put(filters.get(i).getColName(),i);
        }

        QueryWrapper<Inquiry> queryWrapper1 = new QueryWrapper<>();
        // 默认最新和有效
        queryWrapper1.eq("latest",true);
        queryWrapper1.ge("state",0);
        List<FilterCriteria> f1 = new ArrayList<>();
        String[] str1 = {"inquiry_code","inquiry_type","sale_num","expected_time","arranged_time","delay","remark"};
        for(String s : str1){
            if(map.containsKey(s)){
                f1.add(filters.get(map.get(s)));
            }
        }
        CommonUtils.addFilters(f1,queryWrapper1);

        QueryWrapper<Inquiry> queryWrapper2 = new QueryWrapper<>();
        f1 = new ArrayList<>();
        String s2 = "salesman_name";
        if(map.containsKey(s2)){
            f1.add(filters.get(map.get(s2)));
        }else{
            // 设置为空
            f1.add(new FilterCriteria(s2,EQUAL,"$$$"));
        }
        CommonUtils.addFilters(f1,queryWrapper2);

        QueryWrapper<Inquiry> queryWrapper3 = new QueryWrapper<>();
        f1 = new ArrayList<>();
        String s3 = "created_user_name";
        if(map.containsKey(s3)){
            f1.add(filters.get(map.get(s3)));
        }else{
            // 设置为空
            f1.add(new FilterCriteria(s3,EQUAL,"$$$"));
        }
        CommonUtils.addFilters(f1,queryWrapper3);

        QueryWrapper<Inquiry> queryWrapper4 = new QueryWrapper<>();
        f1 = new ArrayList<>();
        String s4 = "customer_type";
        if(map.containsKey(s4)){
            f1.add(filters.get(map.get(s4)));
        }else{
            // 设置为空
            f1.add(new FilterCriteria(s4,EQUAL,"$$$"));
        }
        CommonUtils.addFilters(f1,queryWrapper4);

        QueryWrapper<Inquiry> queryWrapper5 = new QueryWrapper<>();
        f1 = new ArrayList<>();
        String[] str5 = {"item_code","item_name","item_type"};
        for(String s : str5){
            if(map.containsKey(s)){
                f1.add(filters.get(map.get(s)));
            }
        }
        CommonUtils.addFilters(f1,queryWrapper5);

        QueryWrapper<Inquiry> queryWrapper6 = new QueryWrapper<>();
        f1 = new ArrayList<>();
        String s6 = "item_code";
        if(map.containsKey(s6)){
            f1.add(filters.get(map.get(s6)));
        }else{
            // 设置为空
            f1.add(new FilterCriteria(s6,EQUAL,"$$$"));
        }
        CommonUtils.addFilters(f1,queryWrapper6);

        return salesOrderVoMapper.selectListByFilter(queryWrapper1,queryWrapper2,queryWrapper3,queryWrapper4,queryWrapper5,queryWrapper6,offset,limit);
    }

    /**
     * 只是测试
     * @param filters
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> testItemFilter(List<FilterCriteria> filters, int offset, int limit) {

        QueryWrapper<List<Map<String,Object>>> queryWrapper = new QueryWrapper<>();
        CommonUtils.addFilters(filters, queryWrapper);
        return salesOrderVoMapper.getALL(queryWrapper,offset,limit);
    }

    @Override
    public List<Map<String, Object>> getColMaps(Long tableId, Long planId, Long userId) {

        return null;
    }
}
