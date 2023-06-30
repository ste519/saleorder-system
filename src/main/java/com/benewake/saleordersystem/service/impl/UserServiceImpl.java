package com.benewake.saleordersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.mapper.UserMapper;
import com.benewake.saleordersystem.service.UserService;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.benewake.saleordersystem.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年06月30 13:42
 * 描 述： TODO
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


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
        if(user.getType()==null){
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
            user.setAuth(0L);

            userMapper.insert(user);
            return Result.success("添加成功",map);
        }
    }
}
