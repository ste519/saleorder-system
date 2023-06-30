package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author Lcs
 * @since 2023年06月30 16:11
 * 描 述： TODO
 */
@Data
@TableName("fim_login_ticket")
public class LoginTicket {

    @TableId("FIM_ticket_id")
    private Long id;

    @TableField("FIM_user_id")
    private Long userId;

    @TableField("FIM_ticket")
    private String ticket;
    @TableField("FIM_user_status")
    private Integer status;
    @TableField("FIM_expired")
    private Date expired;


}
