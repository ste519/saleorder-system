package com.benewake.saleordersystem.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Lcs
 * @CreateTime 2023年06月27 17:51
 * @Description TODO
 */
@Data
public class Md {
    //FID,FBillNo,FSaleOrgId,FDate,FStockOrgId,FCustomerID
    @ExcelProperty(value = "日期")
    private String FDate;
    @ExcelProperty("要货日期")
    private String FDeliveryDate;
    @ExcelProperty("单据编号")
    private String FBillNo;
    @ExcelProperty("单据状态")
    private String FDocumentStatus;
    @ExcelProperty("销售员")
    private String FSalerId;
    @ExcelProperty("创建人")
    private String FCreatorId;
    @ExcelProperty("客户")
    private String FCustId;
    @ExcelProperty("审核人")
    private String FApproverId;
    @ExcelProperty("关闭状态")
    private String FCloseStatus;
    @ExcelProperty("物料编码")
    private String FMaterialId;
    @ExcelProperty("物料名称")
    private String FMaterialName;
    @ExcelProperty("销售数量")
    private String FQty;
    @ExcelProperty("剩余未出数量（销售基本）")
    private String FBaseRemainOutQty;



}
