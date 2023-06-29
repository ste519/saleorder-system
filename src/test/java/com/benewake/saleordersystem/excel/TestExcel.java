package com.benewake.saleordersystem.excel;

import com.alibaba.excel.EasyExcel;
import com.benewake.saleordersystem.SaleOrderSystemApplication;
import com.benewake.saleordersystem.model.BillMaterialModel;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;

@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class TestExcel {

    @Test
    public void alpheExcel(){
        BillMaterialExcelListener listener = new BillMaterialExcelListener();
        File f = new File("F:\\study\\物料清单_2022121517171911_219769.xlsx");
        EasyExcel.read(f, BillMaterialModel.class,listener).sheet().doRead();
    }

    @Test
    public void k3CloudTest(){
        K3CloudApi api = new K3CloudApi();

    }

}
