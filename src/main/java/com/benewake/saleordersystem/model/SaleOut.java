package com.benewake.saleordersystem.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

/**
 * @author Lcs
 * @since 2023年07月11 10:43
 * 描 述： TODO
 */
@Data
public class SaleOut {
    @ExcelProperty("单据编号")
    private String FBillNo;
    @ExcelProperty("物料编码")
    private String FMaterialID;
    @ExcelProperty("物料名称")
    private String FMaterialName;
    @ExcelProperty("销售数量")
    private String FRealQty;
//    @ExcelIgnore
//    private String FStockOrgId;
    @ExcelProperty("客户名称")
    private String FCustomerID;
    @ExcelProperty("销售员")
    private String FSalesManID;
    @ExcelProperty("销售日期")
    @DateTimeFormat("yyyy-MM-dd")
    private String FDate;
    @ExcelProperty("订单单号")
    private String FSoorDerno;
}
