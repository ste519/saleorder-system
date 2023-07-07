package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Lcs
 * @since 2023年07月07 17:22
 * 描 述： TODO
 */
@Data
@TableName("fim_view_table")
public class View {
    @TableId("view_id")
    private Long viewId;
    @TableField("user_id")
    private Long userId;
    @TableField("view_name")
    private String viewName;
    @TableField("table_id")
    private Long tableId;

}
