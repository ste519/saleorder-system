package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * @author Lcs
 */
@Data
@TableName("user")
public class User {
    @TableId("user_id")
    private Long id;
    @TableField("user_name")
    private String username;
    @TableField("user_password")
    private String password;
    @TableField("user_salt")
    private String salt;
    @TableField("user_type")
    private Long type;
//    //激活码
//    @TableField("activation_code")
//    private String activationCode;
//    //头像路径
//    @TableField("header_url")
//    private String headerUrl;
//    //日期
//    @TableField("create_time")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date createTime;
}
