package com.benewake.saleordersystem.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

/**
 * @author Lcs
 * @since 2023年07月11 14:27
 * 描 述： TODO
 */
@Data
public class Withdraw {
    @ExcelProperty("物料编码")
    private String FMaterialId;
    @ExcelProperty("物料名称")
    private String FMaterialName;
    @ExcelProperty("销售数量")
    private String FRealQty;
//    @ExcelIgnore
//    private String FSaleOrgId;
    @ExcelProperty("客户名称")
    private String FRetcustId;
    @ExcelProperty("销售员")
    private String FSalesManId;
    @ExcelProperty("销售日期")
    @DateTimeFormat("yyyy-MM-dd")
    private String FDate;
    @ExcelProperty("订单单号")
    private String FOrderNo;


}
