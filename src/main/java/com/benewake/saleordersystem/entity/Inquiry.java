package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Lcs
 * @since 2023年07月04 10:38
 * 描 述： TODO
 */
@Data
@TableName("fim_inquiry_table")
public class Inquiry {
    @TableId("inquiry_id")
    private Long inquiryId;
    @TableField("inquiry_code")
    private String inquiryCode;
    @TableField("state")
    private Integer state;
    @TableField("created_time")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date createdTime;
    @TableField("created_user")
    private Long createdUser;
    @TableField("salesman_id")
    private Long salesmanId;
    @TableField("item_id")
    private Long itemId;
    @TableField("customer_id")
    private Long customerId;
    @TableField("sale_num")
    private Long saleNum;
    @TableField("expected_time")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date expectedTime;
    @TableField("arranged_time")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date arrangedTime;
    @TableField("inquiry_type")
    private Integer inquiryType;
    @TableField("remark")
    private String remark;

}
