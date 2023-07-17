package com.benewake.saleordersystem.service.impl;

import com.benewake.saleordersystem.service.AlphaService;
import com.benewake.saleordersystem.utils.PythonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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


}
