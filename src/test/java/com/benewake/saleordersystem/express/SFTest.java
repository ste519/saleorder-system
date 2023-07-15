package com.benewake.saleordersystem.express;

import com.alibaba.fastjson2.JSONObject;
import com.benewake.saleordersystem.SaleOrderSystemApplication;
import com.benewake.saleordersystem.entity.sfexpress.Route;
import com.benewake.saleordersystem.entity.sfexpress.SF_SEARCH_RESULT;
import com.benewake.saleordersystem.model.SaleOut;
import com.benewake.saleordersystem.service.KingDeeService;
import com.benewake.saleordersystem.service.SFExpressService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * @author Lcs
 * @since 2023年07月05 14:33
 * 描 述： TODO
 */
@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class SFTest {
    @Autowired
    SFExpressService sfExpressService;
    @Autowired
    KingDeeService kingDeeService;

    @Test
    public void testSF(){
        SF_SEARCH_RESULT result = sfExpressService.findRoutesByCode("SF2071882625241","4392");

    }

    @Test
    public void unionTest() throws Exception {
        Route route = sfExpressService.getLastestRouteByFCarriageNO("XSDD2306020");
        if(route==null) System.out.println("未发货或无收件人电话");
        else System.out.println(route.toString());
    }
}
