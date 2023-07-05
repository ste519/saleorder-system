package com.benewake.saleordersystem.mapper;

import com.benewake.saleordersystem.SaleOrderSystemApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Lcs
 * @since 2023年07月04 10:44
 * 描 述： TODO
 */
@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class InquiryTest {
    @Autowired
    InquiryMapper inquiryMapper;

    @Test
    public void testList(){
        inquiryMapper.selectList(null).forEach(System.out::println);
    }
}
