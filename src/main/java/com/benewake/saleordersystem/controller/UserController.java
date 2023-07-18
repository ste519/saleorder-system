package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.service.UserService;
import com.benewake.saleordersystem.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    public Result<String> updatePassword(HttpServletRequest request, Map<String,Object> param){
        String oldPassword = (String) param.get("oldPassword");
        String newPassword = (String) param.get("newPassword");
        String rePassword = (String) param.get("rePassword");
        Long userId = Long.parseLong((String)param.get("userid"));
        // 判断空值
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(rePassword)){
            return Result.fail("密码不能为空");
        }
        // 判断新密码两次输入是否一致
        if(!newPassword.equals(rePassword)){
            return Result.fail("两次输入的密码不一致");
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
        }else if(user.getUserType().equals(USER_TYPE_ADMIN)){
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
        }else if(user.getUserType().equals(USER_TYPE_SYSTEM)){
            // 无效 或 系统
            return Result.success("系统修改密码模块");
        }

        return Result.fail("无效用户！");
    }

    /**
     * 根据用户姓名模糊匹配
     * @return
     */
    @PostMapping("/likeList")
    public Result<List<User>> getUserLikeList(Map<String,Object> param){
        String username = (String) param.get("username");
        Long userType = Long.parseLong((String) param.get("userType"));
        return Result.success(userService.getUsernameLikeList(username,userType));
    }

}
