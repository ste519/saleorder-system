package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Lcs
 * @Description TODO
 * @since 2023年06月29 14:33
 */
@Data
@TableName("inquiry_salesman_table")
public class Salesman {
    @TableId("salesman_id")
    Integer id;
    @TableField("salesman_name")
    String name;
}
