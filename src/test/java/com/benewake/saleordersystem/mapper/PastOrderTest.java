package com.benewake.saleordersystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benewake.saleordersystem.SaleOrderSystemApplication;
import com.benewake.saleordersystem.entity.FilterCriteria;
import com.benewake.saleordersystem.entity.Past.PastOrder;
import com.benewake.saleordersystem.service.AlphaService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import com.benewake.saleordersystem.utils.CommonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.el.ArrayELResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月04 15:05
 * 描 述： TODO
 */
@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class PastOrderTest implements BenewakeConstants {
    @Autowired
    public PastOrderMapper pastOrderMapper;

    @Autowired
    public AlphaService alphaService;

    @Test
    public void testReload(){
        pastOrderMapper.reloadLocalOrders();
    }
    @Test
    public void testList(){
        List<FilterCriteria> filters = new ArrayList<>();
        filters.add(new FilterCriteria("salesman_name",EQUAL,"刘双双"));
        filters.add(new FilterCriteria("order_id",GREATER_OR_EQUAL,216));
        filters.add(new FilterCriteria("sale_time",GREATER_OR_EQUAL,"2021-08-17"));
        filters.add(new FilterCriteria("customer_name",LIKE,"A"));


        alphaService.getList(filters).forEach(System.out::println);
    }
}
