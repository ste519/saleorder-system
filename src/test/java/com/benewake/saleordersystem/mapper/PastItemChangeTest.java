package com.benewake.saleordersystem.mapper;

import com.benewake.saleordersystem.SaleOrderSystemApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Lcs
 * @since 2023年06月30 10:45
 * 描 述： TODO
 */
@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class PastItemChangeTest {
    @Autowired
    PastItemChangeMapper pastItemChangeMapper;

    @Test
    public void getList(){
        pastItemChangeMapper.selectList(null).forEach(System.out::println);
    }
}
