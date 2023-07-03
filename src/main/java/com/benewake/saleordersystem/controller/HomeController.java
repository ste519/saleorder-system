package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.service.UserService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import com.benewake.saleordersystem.utils.HostHolder;
import com.benewake.saleordersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年06月30 11:49
 * 描 述： TODO
 */
@RestController
public class HomeController implements BenewakeConstants {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    @GetMapping("/index")
    @LoginRequired
    public Result<String> index() {
        return Result.success();
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<Map<String, Object>> add(User user) {
        //System.out.println("新增用户信息："+user.toString());

        return userService.addUser(user);
    }


    @GetMapping("/login")
    public Result<Map<String,Object>> toLogin(){
        Map<String,Object> map = new HashMap<>();
        map.put("msg","进入登录页面！");
        return Result.success(map);
    }
    @PostMapping("/login")
    @ResponseBody
    public Result<Map<String,Object>> login(String username, String password, Model model, HttpServletResponse response){
        if(hostHolder.getUser() != null) {
            // 当前已存在登录用户
            Map<String,Object> map = new HashMap<>();
            map.put("loginMessage","当前已有账号登录，请先退出当前账号！");
            return Result.fail(map);
        }
        Map<String,Object> map = userService.login(username,password);
        if (map.containsKey("ticket")) {
            //验证成功 设置Cookie并返回成功信息
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(DEFAULT_EXPIRED_SECONDS);
            response.addCookie(cookie);

            return Result.success(map);
        }else{
            // 验证失败 返回失败信息
            return Result.fail(map);
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public Result<Map<String,Object>> logout(@CookieValue("ticket") String ticket){
        return Result.success(userService.logout(ticket));
    }
}
