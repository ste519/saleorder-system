package com.benewake.saleordersystem.KindDee;

import com.benewake.saleordersystem.SaleOrderSystemApplication;
import com.benewake.saleordersystem.entity.Transfer.CreateIdToName;
import com.benewake.saleordersystem.entity.Transfer.CustIdToName;
import com.benewake.saleordersystem.entity.Md;
import com.benewake.saleordersystem.entity.Transfer.MaterialIdToName;
import com.benewake.saleordersystem.entity.Transfer.SalerIdToName;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.entity.QueryParam;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import jnr.ffi.Struct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Lcs
 * @CreateTime 2023年06月27 17:43
 * @Description TODO
 */
@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class DemoTest {

    @Autowired
    private K3CloudApi k3CloudApi;

    @Test
    public void tDemo() throws Exception {

        // 使用方法配置
        IdentifyInfo iden = new IdentifyInfo();
        iden.setUserName("TF系列交付管理信息系统");
        iden.setAppId("252117_346u3ckO6Pl/Qe/L667BQaWr1JXb2KKH");
        iden.setdCID("20210222142620903");
        iden.setAppSecret("d7cc71aa2cc74d4eb06f21fa322f2082");
        iden.setlCID(2052);
        iden.setOrgNum("100");
        iden.setServerUrl("https://benewake.test.ik3cloud.com/k3cloud/");
        K3CloudApi api = new K3CloudApi(iden);


//        String json = "{\n" +
//                "    \"FormId\": \"BD_Material\",\n" +
//                "    \"FieldKeys\": \"FMATERIALID,FNumber,FName,FCreateOrgId,FUseOrgId,\",\n" +
////                "    \"FilterString\": \"FNumber=\'" + FNumber + "\'\",\n" +
//                "    \"FilterString\": [] ,\n" +
//                "    \"OrderString\": \"\",\n" +
//                "    \"TopRowCount\": 0,\n" +
//                "    \"StartRow\": 0,\n" +
//                "    \"Limit\": 2000,\n" +
//                "    \"SubSystemId\": \"\"\n" +
//                "}";
        QueryParam queryParam = new QueryParam();
        queryParam.setFormId("SAL_SaleOrder");
        queryParam.setFieldKeys("FDate,FDeliveryDate,FBillNo,FDocumentStatus,FSalerId,FCreatorId,FCustId,FApproverId,FCloseStatus,FMaterialId,FMaterialName,FQty,FBaseRemainOutQty");
        // 条件筛选
        //List<String> queryFilters = new ArrayList<>();
        //queryParam.setFilterString("FCreateOrgId = 1");
        List<String> queryFilters = new ArrayList<>();

        //queryFilters.add(String.format("FDate >= '%s'","2023-06-01"));
        //queryFilters.add(String.format("FCustId = '%s'","452119"));
        queryFilters.add(String.format("FQty >= '%s'","6"));
        queryParam.setFilterString(String.join(" and ",queryFilters));
        queryParam.setLimit(10);
        List<Md> result = k3CloudApi.executeBillQuery(queryParam, Md.class);
        System.out.println("查询到的数据数量: " + result.size());
        SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 数据处理
        for(Md m : result){
            // 时间格式处理
            m.setFDate(ft2.format(ft1.parse(m.getFDate())));
            m.setFDeliveryDate(ft2.format(ft1.parse(m.getFDeliveryDate())));
            // 状态处理
//            String status = m.getFDocumentStatus();
//            switch (status){
//                case "A":
//                    m.setFDocumentStatus("创建");
//                    break;
//                case "B":
//                    m.setFDocumentStatus("审核中");
//                    break;
//                case "Z":
//                    m.setFDocumentStatus("暂存");
//                    break;
//                case "C":
//                    m.setFDocumentStatus("已审核");
//                    break;
//                case "D":
//                    m.setFDocumentStatus("重新审核");
//            }
//            m.setFCloseStatus(m.getFCloseStatus().equals("A")?"已关闭":"未关闭");
//            // 替换客户id->客户名
//            queryParam = new QueryParam();
//            queryParam.setFormId("BD_Customer");
//            queryParam.setFieldKeys("FCustId,FName");
//            queryParam.setFilterString(String.format("FCustId = [%s]",m.getFCustId()));
//            queryParam.setLimit(1);
//            List<CustIdToName> cidToName = api.executeBillQuery(queryParam, CustIdToName.class);
//            m.setFCustId(cidToName.get(0).getFName());
//            // 替换销售员Id->销售员
//
//            // 替换创建人和审核人Id->名字
//            queryParam = new QueryParam();
//            queryParam.setFormId("SEC_User");
//            queryParam.setFieldKeys("FUserID,FName");
//            queryParam.setFilterString(String.format("FUserID = [%s]",m.getFCreatorId()));
//            queryParam.setLimit(1);
//            List<CreateIdToName> crtidToName = api.executeBillQuery(queryParam, CreateIdToName.class);
//            if(m.getFCreatorId().equals(m.getFApproverId())){
//                m.setFCreatorId(crtidToName.get(0).getFName());
//                m.setFApproverId(m.getFCreatorId());
//            }else{
//                m.setFCreatorId(crtidToName.get(0).getFName());
//                queryParam = new QueryParam();
//                queryParam.setFormId("SEC_User");
//                queryParam.setFieldKeys("FUserID,FName");
//                queryParam.setFilterString(String.format("FUserID = [%s]",m.getFApproverId()));
//                queryParam.setLimit(1);
//                crtidToName = api.executeBillQuery(queryParam, CreateIdToName.class);
//                m.setFApproverId(crtidToName.get(0).getFName());
//            }
//            // 替换物料ID->物料名称
//            queryParam = new QueryParam();
//            queryParam.setFormId("BD_MATERIAL");
//            queryParam.setFieldKeys("FMaterialId,FNumber");
//            queryParam.setFilterString(String.format("FMaterialId = [%s]",m.getFMaterialId()));
//            queryParam.setLimit(1);
//            List<MaterialIdToName> midToName = api.executeBillQuery(queryParam, MaterialIdToName.class);
//            m.setFMaterialId(midToName.get(0).getFNumber());

            System.out.println(m.toString());
        }
        //1、创建一个文件对象
        File excelFile = new File("F:/销售订单测试.xlsx");
        System.out.println(CommonUtils.writeExcel(excelFile,result,Md.class));
    }

    @Test
    public void addCustIdToName() throws Exception {
        IdentifyInfo iden = new IdentifyInfo();
        iden.setUserName("TF系列交付管理信息系统");
        iden.setAppId("252117_346u3ckO6Pl/Qe/L667BQaWr1JXb2KKH");
        iden.setdCID("20210222142620903");
        iden.setAppSecret("d7cc71aa2cc74d4eb06f21fa322f2082");
        iden.setlCID(2052);
        iden.setOrgNum("100");
        iden.setServerUrl("https://benewake.test.ik3cloud.com/k3cloud/");
        K3CloudApi api = new K3CloudApi(iden);

        QueryParam queryParam = new QueryParam();
        queryParam.setFormId("BD_OPERATOR");
        queryParam.setFieldKeys("FSalerId,FName");
        //queryParam.setFilterString(String.format("FCustId = [%s]",102949));
        //queryParam.setLimit(1);
        List<SalerIdToName> result = api.executeBillQuery(queryParam, SalerIdToName.class);
        File file = new File("F:/销售员ID映射表.xlsx");
        CommonUtils.writeExcel(file,result, SalerIdToName.class);
    }
}
