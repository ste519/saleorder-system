package com.benewake.saleordersystem.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Lcs
 * @since 2023年07月03 17:03
 * 描 述： TODO
 */
@Data
@TableName("fim_customer_table")
public class Customer {
    @ExcelProperty("客户ID")
    @TableId("customer_id")
    private Long fCustId;

    @ExcelProperty("客户名")
    @TableField("customer_name")
    private String fName;
}
