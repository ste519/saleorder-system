package com.benewake.saleordersystem.mapper;

import com.benewake.saleordersystem.SaleOrderSystemApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Lcs
 * @since 2023年07月04 15:05
 * 描 述： TODO
 */
@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class PastOrderTest {
    @Autowired
    public PastOrderMapper pastOrderMapper;

    @Test
    public void testReload(){
        pastOrderMapper.reloadLocalOrders();
    }
}
