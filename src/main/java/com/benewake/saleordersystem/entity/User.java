package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
@TableName("user")
public class User {
    @TableId
    private Long id;
    private String username;
    private String password;
    //盐
    private String salt;
    //邮箱
    private String email;
    //用户类型
    private Long type;
    //状态
    private Long status;
    //激活码
    @TableField("activation_code")
    private String activationCode;
    //头像路径
    @TableField("header_url")
    private String headerUrl;
    //日期
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
