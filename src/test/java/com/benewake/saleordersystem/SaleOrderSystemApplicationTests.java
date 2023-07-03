package com.benewake.saleordersystem;

import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.entity.api.holiday.Holiday;
import com.benewake.saleordersystem.mapper.UserMapper;
import com.benewake.saleordersystem.service.AlphaService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import com.benewake.saleordersystem.utils.api.HolidayUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
class SaleOrderSystemApplicationTests implements BenewakeConstants {

    @Autowired
    private UserMapper userMap;

    @Autowired
    private AlphaService alphaService;


    @Test
    void testAsync() throws InterruptedException {
        alphaService.alphaPython();
        System.out.println("下一个任务开始");
        Thread.sleep(30000);

    }
    @Test
    void contextLoads() {
//        User a = userMap.selectUserByName("liao");
//
//        System.out.println(a.toString());

    }

    @Test
    void testHoliday() throws InterruptedException {
        List<Holiday> list = HolidayUtils.getMultiHolidays(System.currentTimeMillis(),30,false);
        for(Holiday h : list){
            System.out.println(h.toString());
        }

    }

}
