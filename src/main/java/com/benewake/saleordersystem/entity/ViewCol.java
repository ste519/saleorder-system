package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Lcs
 * @since 2023年07月07 17:11
 * 描 述： TODO
 */
@Data
@TableName("fim_view_col_table")
public class ViewCol {
    @TableId("view_id")
    private Long viewId;
    @TableField("col_id")
    private Long colId;
    @TableField("col_value")
    private String colValue;
    @TableField("col_seq")
    private int colSeq;
}
