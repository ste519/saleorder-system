package com.benewake.saleordersystem.service;

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

}
