package com.benewake.saleordersystem.express;

import com.alibaba.fastjson2.JSONObject;
import com.benewake.saleordersystem.SaleOrderSystemApplication;
import com.benewake.saleordersystem.entity.sfexpress.Route;
import com.benewake.saleordersystem.entity.sfexpress.SF_SEARCH_RESULT;
import com.benewake.saleordersystem.service.SFExpressService;
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

    @Test
    public void testSF(){
        SF_SEARCH_RESULT result = sfExpressService.findRoutesByCode("SF1688857087748","8562");
        String msgData = JSONObject.parseObject(result.getApiResultData()).getString("msgData");
        String routeResps = JSONObject.parseObject(msgData).getJSONArray("routeResps").get(0).toString();
        List<Route> routes = JSONObject.parseObject(routeResps).getList("routes", Route.class);
        routes.forEach(System.out::println);
    }
}
