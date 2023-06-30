package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.mapper.UserMapper;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.benewake.saleordersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @author Lcs
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    @ResponseBody
    public Result<User> alpheTime(int aa){
        System.out.println( Result.success().toString());
        return  Result.fail();
    }

}
