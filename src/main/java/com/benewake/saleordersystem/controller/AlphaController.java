package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.mapper.UserMapper;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.benewake.saleordersystem.utils.Result;
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
    public Result<User> alpheTime(int aa, User ss){
        return null;
    }

}
