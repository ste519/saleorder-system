package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.service.UserService;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.benewake.saleordersystem.utils.CookieUtil;
import com.benewake.saleordersystem.utils.HostHolder;
import com.benewake.saleordersystem.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lcs
 * @since 2023年07月07 15:29
 * 描 述： TODO
 */
@Controller
@ResponseBody
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/updatePwd")
    @LoginRequired
    public Result<String> updatePassword(HttpServletRequest request,String oldPassword, String newPassword){
        // 判断空值
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)){
            return Result.fail("密码不能为空");
        }
        // 判断新密码是否符合条件

        // 判断旧密码是否正确
        User user = hostHolder.getUser();
        String password = CommonUtils.md5(oldPassword+user.getSalt());
        if(!password.equals(user.getPassword())){
            return Result.fail("旧密码错误");
        }

        // 修改密码
        userService.updatePassword(user.getId(),newPassword);
        // 设置ticket过期 则重新登录
        String ticket = CookieUtil.getValue(request,"ticket");
        userService.logout(ticket);

        return Result.success("修改成功,请重新登录!");
    }
}
