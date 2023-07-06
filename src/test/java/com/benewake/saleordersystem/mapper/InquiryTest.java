package com.benewake.saleordersystem.mapper;

import com.benewake.saleordersystem.SaleOrderSystemApplication;
import com.benewake.saleordersystem.service.InquiryService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月04 10:44
 * 描 述： TODO
 */
@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class InquiryTest implements BenewakeConstants {
    @Autowired
    InquiryService inquiryService;

    @Test
    public void testList(){
        Map<String, Map<String,Object>> filters = new HashMap<>();
        Map<String,Object> f1 = new HashMap<>();

    }
}
