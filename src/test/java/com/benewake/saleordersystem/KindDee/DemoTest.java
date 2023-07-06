package com.benewake.saleordersystem.KindDee;

import com.benewake.saleordersystem.SaleOrderSystemApplication;
import com.benewake.saleordersystem.entity.Md;
import com.benewake.saleordersystem.entity.Transfer.*;
import com.benewake.saleordersystem.service.KingDeeService;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.entity.QueryParam;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private KingDeeService kingDeeService;

    @Test
    public void tDemo() throws Exception {

        QueryParam queryParam = new QueryParam();
        queryParam.setFormId("SAL_SaleOrder");
        queryParam.setFieldKeys("FDate,FDeliveryDate,FBillNo,FDocumentStatus,FSalerId,FCreatorId,FCustId,FApproverId,FCloseStatus,FMaterialId,FMaterialName,FQty,FBaseRemainOutQty");
        // 条件筛选
        List<String> queryFilters = new ArrayList<>();

        queryFilters.add(String.format("FDate >= '%s'","2023-06-01"));
        //queryFilters.add(String.format("FCustId = '%s'","452119"));
        //queryFilters.add(String.format("FQty >= '%s'","6"));
        queryParam.setFilterString(String.join(" and ",queryFilters));
        queryParam.setLimit(200);
        List<Md> result = k3CloudApi.executeBillQuery(queryParam, Md.class);
        System.out.println("查询到的数据数量: " + result.size());
        // 时间格式
        SimpleDateFormat ft1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 数据处理
        // 客户映射表
        queryParam = new QueryParam();
        queryParam.setFormId("BD_Customer");
        queryParam.setFieldKeys("FCustId,FName");
        List<CustIdToName> cidToName = k3CloudApi.executeBillQuery(queryParam, CustIdToName.class);
        Map<String,String> citn = new HashMap<>();
        cidToName.forEach(c -> {
            citn.put(c.getFCustId(),c.getFName());
        });
        cidToName = null;
        // 创建人和审核人映射表
        queryParam = new QueryParam();
        queryParam.setFormId("SEC_User");
        queryParam.setFieldKeys("FUserID,FName");
        List<CreateIdToName> crtidToName = k3CloudApi.executeBillQuery(queryParam, CreateIdToName.class);
        Map<String,String> critn = new HashMap<>();
        crtidToName.forEach(c->{
            critn.put(c.getFUserID(),c.getFName());
        });
        crtidToName = null;
        // 物料映射表
        queryParam = new QueryParam();
        queryParam.setFormId("BD_MATERIAL");
        queryParam.setFieldKeys("FMaterialId,FNumber");
        List<MaterialIdToName> midToName = k3CloudApi.executeBillQuery(queryParam, MaterialIdToName.class);
        Map<String,String> mtn = new HashMap<>();
        midToName.forEach(c->{
            mtn.put(c.getFMaterialId(),c.getFNumber());
        });
        midToName = null;
        for(Md m : result){
            // 时间格式处理
            m.setFDate(ft2.format(ft1.parse(m.getFDate())));
            m.setFDeliveryDate(ft2.format(ft1.parse(m.getFDeliveryDate())));
            // 状态处理
            m.setFDocumentStatus(CommonUtils.getOrderStatus(m.getFDocumentStatus()));
            m.setFCloseStatus(CommonUtils.getIsClose(m.getFCloseStatus()));
            // 信息替换
            m.setFCustId(citn.get(m.getFCustId()));
            m.setFCreatorId(critn.get(m.getFCreatorId()));
            m.setFApproverId(critn.get(m.getFApproverId()));
            m.setFMaterialId(mtn.get(m.getFMaterialId()));
            // 替换客户id->客户名
//            queryParam = new QueryParam();
//            queryParam.setFormId("BD_Customer");
//            queryParam.setFieldKeys("FCustId,FName");
//            queryParam.setFilterString(String.format("FCustId = %s",m.getFCustId()));
//            queryParam.setLimit(1);
//            List<CustIdToName> cidToName = k3CloudApi.executeBillQuery(queryParam, CustIdToName.class);
//            m.setFCustId(cidToName.get(0).getFName());
            // 替换销售员Id->销售员
//            queryParam = new QueryParam();
//            queryParam.setFormId("BD_Saler");
//            queryParam.setFieldKeys("FNumber,FName");
//            queryParam.setFilterString(String.format("FNumber = %s",m.getFSalerId()));
//            queryParam.setLimit(1);
//            List<SalerIdToName> list = k3CloudApi.executeBillQuery(queryParam,SalerIdToName.class);
//            m.setFSalerId(list.get(0).getFName());
            // 替换创建人和审核人Id->名字
//            queryParam = new QueryParam();
//            queryParam.setFormId("SEC_User");
//            queryParam.setFieldKeys("FUserID,FName");
//            queryParam.setFilterString(String.format("FUserID = %s",m.getFCreatorId()));
//            queryParam.setLimit(1);
//            List<CreateIdToName> crtidToName = k3CloudApi.executeBillQuery(queryParam, CreateIdToName.class);
//            if(m.getFCreatorId().equals(m.getFApproverId())){
//                m.setFCreatorId(crtidToName.get(0).getFName());
//                m.setFApproverId(m.getFCreatorId());
//            }else{
//                m.setFCreatorId(crtidToName.get(0).getFName());
//                queryParam = new QueryParam();
//                queryParam.setFormId("SEC_User");
//                queryParam.setFieldKeys("FUserID,FName");
//                queryParam.setFilterString(String.format("FUserID = %s",m.getFApproverId()));
//                queryParam.setLimit(1);
//                crtidToName = k3CloudApi.executeBillQuery(queryParam, CreateIdToName.class);
//                m.setFApproverId(crtidToName.get(0).getFName());
//            }
            // 替换物料ID->物料名称
//            queryParam = new QueryParam();
//            queryParam.setFormId("BD_MATERIAL");
//            queryParam.setFieldKeys("FMaterialId,FNumber");
//            queryParam.setFilterString(String.format("FMaterialId = [%s]",m.getFMaterialId()));
//            queryParam.setLimit(1);
//            List<MaterialIdToName> midToName = k3CloudApi.executeBillQuery(queryParam, MaterialIdToName.class);
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

    @Test
    public void testFun() throws Exception {
        String fromId = "SAL_SaleOrder";
        List<String> fieldKeys = new ArrayList<>();
        fieldKeys.add("FDate");
        fieldKeys.add("FDeliveryDate");
        fieldKeys.add("FBillNo");
        fieldKeys.add("FDocumentStatus");
        fieldKeys.add("FSalerId");
        fieldKeys.add("FCreatorId");
        fieldKeys.add("FCustId");
        fieldKeys.add("FApproverId");
        fieldKeys.add("FCloseStatus");
        fieldKeys.add("FMaterialId");
        fieldKeys.add("FMaterialName");
        fieldKeys.add("FQty");
        fieldKeys.add("FBaseRemainOutQty");
        List<String> queryFilters = new ArrayList<>();
        queryFilters.add(String.format("FDate >= '%s'","2023-06-01"));
        queryFilters.add(String.format("FQty >= '%s'","6"));
        List<Md> res = kingDeeService.searchData(fromId,fieldKeys,String.join(" and ",queryFilters),1,10, Md.class);
        res.forEach(System.out::println);
    }

    @Test
    public void te() throws Exception {
        QueryParam queryParam = new QueryParam();
        queryParam.setFormId("BD_OPERATOR");
        queryParam.setFieldKeys("FStaffId");
        queryParam.setLimit(200);
        List<SalerT> list = k3CloudApi.executeBillQuery(queryParam, SalerT.class);
        File file = new File("F:/测试.xlsx");
        CommonUtils.writeExcel(file,list, SalerT.class);
    }
}
