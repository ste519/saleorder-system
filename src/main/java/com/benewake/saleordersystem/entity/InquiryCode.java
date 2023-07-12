package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Lcs
 * @since 2023年07月08 14:05
 * 描 述： TODO
 */
@Data
@TableName("fim_inquiry_code_table")
public class InquiryCode {
    @TableField("inquiry_type")
    private Long inquiryType;
    @TableField("today_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date todayDate;
    @TableField("FIM_user_id")
    private Long userId;
}
