package com.benewake.saleordersystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Lcs
 * @since 2023年08月07 11:00
 * 描 述： TODO
 */
@Data
@TableName("fim_item_standard")
public class ItemStandard {
    @TableId("id")
    private Long id;
    @TableField("item_id")
    private Long itemId;
    @TableField("quantitative")
    private Long quantitative;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date createTime;
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date updateTime;
    @TableField("is_deleted")
    private Integer deleted;

}
