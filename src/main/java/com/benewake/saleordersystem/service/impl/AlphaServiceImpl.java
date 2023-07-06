package com.benewake.saleordersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benewake.saleordersystem.entity.FilterCriteria;
import com.benewake.saleordersystem.entity.Past.PastOrder;
import com.benewake.saleordersystem.mapper.PastOrderMapper;
import com.benewake.saleordersystem.service.AlphaService;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.benewake.saleordersystem.utils.PythonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AlphaServiceImpl implements AlphaService {

    @Async
    @Override
    public void alphaPython() {
        String py = "F:\\study\\pythonStudy\\FJSP2\\main.py";
        String[] args = {"test_data/origin_data/history_input_time.csv","test_data/origin_data/Order_processing_time_sequence.csv","test_data/origin_data/order_history_product.csv"};

        try {
            Thread.sleep(1000);
            System.out.println("python脚本开始");
            System.out.println(PythonUtils.doPython(py,args));
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("已完成python脚本");
    }

    @Autowired
    private PastOrderMapper pastOrderMapper;
    public List<PastOrder> getList(List<FilterCriteria> filters){
        QueryWrapper<PastOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("order_id","item_code");
        //,"item_code","customer_name","salesman_name,sale_num"
        CommonUtils.addFilters(filters,queryWrapper);
        return pastOrderMapper.selectList(queryWrapper);
    }
}
