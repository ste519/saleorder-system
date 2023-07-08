package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
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
@ResponseBody
public class HomeController implements BenewakeConstants {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    /**
     * index测试
     */
    @GetMapping("/index")
    @LoginRequired
    public Result<String> index() {
        return Result.success();
    }



    @GetMapping("/login")
    public Result<Map<String,Object>> toLogin(){
        Map<String,Object> map = new HashMap<>(1);
        map.put("msg","进入登录页面！");
        return Result.success(map);
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @param model
     * @param response
     * @return 登录信息
     */
    @PostMapping("/login")
    public Result<Map<String,Object>> login(String username, String password, HttpServletResponse response){
        if(hostHolder.getUser() != null) {
            // 当前已存在登录用户
            Map<String,Object> map = new HashMap<>(1);
            map.put("loginMessage","当前已有账号登录，请先退出当前账号！");
            return Result.fail(map);
        }
        // 尝试登录
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

    /**
     * 用户登出
     * @param ticket
     * @return
     */
    @GetMapping("/logout")
    public Result<Map<String,Object>> logout(@CookieValue("ticket") String ticket){
        return Result.success(userService.logout(ticket));
    }
}
