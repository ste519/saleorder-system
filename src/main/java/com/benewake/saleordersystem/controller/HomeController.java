package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.service.UserService;
import com.benewake.saleordersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Lcs
 * @since 2023年06月30 11:49
 * 描 述： TODO
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public Result<String> index() {
        return Result.success();
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<Map<String, Object>> add(User user) {
        System.out.println("用户信息："+user.toString());

        return userService.addUser(user);
    }
}
