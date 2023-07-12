package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Lcs
 * @since 2023年07月03 18:21
 * 描 述： TODO
 */
@Data
@TableName("fim_item_table")
public class Item {
    @TableId("item_id")
    private Long id;
    @TableField("item_code")
    private String itemCode;
    @TableField("item_name")
    private String itemName;
    @TableField("item_type")
    private int itemType;

}
