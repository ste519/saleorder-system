package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.entity.sfexpress.Route;
import com.benewake.saleordersystem.mapper.UserMapper;
import com.benewake.saleordersystem.service.SFExpressService;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.benewake.saleordersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Lcs
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SFExpressService sFExpressService;

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<String,Object>> alpheTime(Integer aa){

        Map<String,Object> map = new HashMap<>();

        List<Route> routes = CommonUtils.paresRoutes(sFExpressService.findRoutesByCode("SF1688857087748","8562"));
        map.put("routes",routes);


        return  Result.success(map);
    }

}
