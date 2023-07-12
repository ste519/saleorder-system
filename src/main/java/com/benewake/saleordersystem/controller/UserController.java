package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.service.UserService;
import com.benewake.saleordersystem.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月07 15:29
 * 描 述： TODO
 */
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController implements BenewakeConstants {
    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/updatePwd")
    @LoginRequired
    public Result<String> updatePassword(HttpServletRequest request,String oldPassword, String newPassword,Long userId){
        // 判断空值
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)){
            return Result.fail("密码不能为空");
        }
        // 判断新密码是否符合条件

        // 判断旧密码是否正确(管理员不需要)
        User user = hostHolder.getUser();
        // 普通用户
        if(user.getUserType().equals(USER_TYPE_SALESMAN) || user.getUserType().equals(USER_TYPE_VISITOR)){
            String password = CommonUtils.md5(oldPassword+user.getSalt());
            if(!password.equals(user.getPassword())){
                return Result.fail("旧密码错误,请输入正确的密码!");
            }
            // 修改密码
            userService.updatePassword(user.getId(),newPassword);
            // 设置ticket过期 则重新登录
            String ticket = CookieUtil.getValue(request,"ticket");
            userService.logout(ticket);
            return Result.success("修改成功,请重新登录!");
        }else if(user.getUserType()==USER_TYPE_ADMIN){
            // 空值处理
            if(userId == null){
                return Result.fail("要修改的用户id为空");
            }
            // 管理员修改用户密码
            int res = userService.updatePassword(userId,newPassword);
            if(res != -1){
                return Result.success("修改成功！");
            }else{
                return Result.fail("用户id不存在！");
            }
        }

        return Result.fail("发生未知错误，请重试");
    }

    /**
     * 添加新用户（需管理员权限 目前没设置)
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result<Map<String, Object>> add(User user) {
        //System.out.println("新增用户信息："+user.toString());

        return userService.addUser(user);
    }

    @PostMapping("/likeList")
    public Result<List<User>> getUserLikeList(String username){
        return Result.success(userService.getSalesmanLikeList(username));
    }

}
