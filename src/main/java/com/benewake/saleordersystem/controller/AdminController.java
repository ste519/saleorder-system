package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.service.UserService;
import com.benewake.saleordersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月06 15:34
 * 描 述： TODO
 */
@ResponseBody
@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * 添加新用户（需管理员权限 目前没设置)
     * @return
     */
    @PostMapping("/add")
    public Result<Map<String, Object>> add(@RequestBody Map<String,Object> param) {
        //System.out.println("新增用户信息："+user.toString());
        User user = (User) param.get("user");
        return userService.addUser(user);
    }


}
