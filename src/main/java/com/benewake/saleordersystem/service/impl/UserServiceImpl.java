package com.benewake.saleordersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benewake.saleordersystem.entity.LoginTicket;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.mapper.LoginTicketMapper;
import com.benewake.saleordersystem.mapper.UserMapper;
import com.benewake.saleordersystem.service.UserService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.benewake.saleordersystem.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年06月30 13:42
 * 描 述： TODO
 */
@Service
public class UserServiceImpl implements UserService, BenewakeConstants {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;


    @Override
    public Result<Map<String, Object>> addUser(User user) {
        Map<String,Object> map = new HashMap<>();
        // 空处理 程序错误 抛出异常
        if(null == user){
            throw new IllegalArgumentException("User cannot be null");
        }
        // 内容缺失
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","用户名不能为空！");
            return Result.fail(map);
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空!");
            return Result.fail(map);
        }
        if(user.getUserType()==null){
            map.put("typeMsg","用户类型不能为空！");
            return Result.fail(map);
        }

        // 验证用户名是否唯一
        QueryWrapper<User> queryWrap = new QueryWrapper<>();
        queryWrap.eq("FIM_user_name",user.getUsername());
        User u = userMapper.selectOne(queryWrap);
        if(u != null){
            map.put("usernameMsg","用户已存在！");
            return Result.fail(map);
        }else{
            // 加密
            user.setSalt(CommonUtils.generateUUID().substring(0, 5));
            user.setPassword(CommonUtils.md5(user.getPassword() + user.getSalt()));

            // 设置默认参数
            user.setUserConllection(0L);

            userMapper.insert(user);
            return Result.success("添加成功",map);
        }
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String,Object> map = new HashMap<>();

        //空值处理
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空！");
            return map;
        }
        // 查询用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("FIM_user_name",username);
        User u = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (null == u) {
            map.put("usernameMsg", "该账号不存在！");
            return map;
        }
        // 验证密码
        password = CommonUtils.md5(password + u.getSalt());
        if (!password.equals(u.getPassword())) {
            map.put("passwordMsg", "密码错误！");
            return map;
        }

        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(u.getId());
        loginTicket.setTicket(CommonUtils.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + DEFAULT_EXPIRED_SECONDS));
        // 持久化
        loginTicketMapper.insert(loginTicket);

        map.put("ticket",loginTicket.getTicket());
        return map;

    }

    @Override
    public Map<String, Object> logout(String ticket) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(ticket)){
            map.put("ticketMessage","ticket不能为空！");
            return map;
        }
        // 条件查询
        QueryWrapper<LoginTicket> qw = new QueryWrapper<>();
        qw.eq("FIM_ticket",ticket);
        LoginTicket loginTicket = loginTicketMapper.selectOne(qw);
        if(null == loginTicket || loginTicket.getStatus() != 0 || !loginTicket.getExpired().after(new Date())){
            map.put("ticketMessage","ticket不存在或已失效");
            return map;
        }
        loginTicket.setStatus(1);
        loginTicketMapper.updateById(loginTicket);
        map.put("ticketMessage","退出成功！");
        return map;
    }

    @Override
    public LoginTicket findLoginTicket(String ticket) {
        QueryWrapper<LoginTicket> qw = new QueryWrapper<>();
        qw.eq("FIM_ticket",ticket);
        LoginTicket loginTicket = loginTicketMapper.selectOne(qw);
        return loginTicket;
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public int updateUserType(Long id, Long type) {
        User u = userMapper.selectById(id);
        u.setUserType(type);
        return userMapper.updateById(u);
    }

    @Override
    public int updateUsername(Long id, String username) {
        User u = userMapper.selectById(id);
        u.setUsername(username);
        return userMapper.updateById(u);
    }

    @Override
    public int updatePassword(Long id, String password) {
        User u = userMapper.selectById(id);
        // 加密存储
        u.setPassword(CommonUtils.md5(password+u.getSalt()));
        return userMapper.updateById(u);
    }
}
