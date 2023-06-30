package com.benewake.saleordersystem.service;

import com.benewake.saleordersystem.entity.LoginTicket;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.utils.Result;

import java.util.Map;

/**
 * @author Lcs
 */
public interface UserService {

    /**
     * 添加新的用户
     * @param user
     * @return
     */
    Result<Map<String,Object>> addUser(User user);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    Map<String,Object> login(String username,String password);

    /**
     * 退出登录状态
     * @param ticket
     * @return
     */
    Map<String,Object> logout(String ticket);

    /**
     * 获取登录凭证
     * @param ticket
     * @return
     */
    LoginTicket findLoginTicket(String ticket);

    /**
     * 根据用户Id查找用户
     * @param id
     * @return
     */
    User findUserById(Long id);

}
