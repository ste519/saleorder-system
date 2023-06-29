package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.mapper.UserMapper;
import com.benewake.saleordersystem.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/index")
    @ResponseBody
    public User alpheTime(){
        //throw new RuntimeException("iii");

        User user = new User();
        user.setId(1L);
        user.setUsername("杜兰特");
        user.setPassword("123456");
        user.setSalt(CommonUtils.generateUUID());
        user.setType(0L);
        System.out.println(user.toString());
        return user;
    }

}
