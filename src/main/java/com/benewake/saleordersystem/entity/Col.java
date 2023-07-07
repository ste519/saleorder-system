package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Lcs
 * @since 2023年07月06 17:26
 * 描 述： TODO
 */
@Data
@TableName("FIM_col_table")
public class Col {
    @TableId("col_id")
    private Long colId;

    @TableField("col_name_ENG")
    private String colNameENG;
    @TableField("col_name_CN")
    private String colNameCN;
    @TableField("table_id")
    private Long tableId;
}
