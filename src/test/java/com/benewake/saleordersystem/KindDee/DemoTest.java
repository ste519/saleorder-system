package com.benewake.saleordersystem.KindDee;

import com.benewake.saleordersystem.SaleOrderSystemApplication;
import com.benewake.saleordersystem.entity.Md;
import com.kingdee.bos.webapi.entity.IdentifyInfo;
import com.kingdee.bos.webapi.entity.QueryParam;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

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
        iden.setUserName("demo");
        iden.setAppId("225649_7ZbM6dDO0qrVXXUKX/Xs09wH2u5d4rLE");
        iden.setdCID("6304ba61219bf5");
        iden.setAppSecret("2bb1d972f3574a46aebee03cdc80aeae");
        iden.setlCID(2052);
        iden.setServerUrl("https://apiexp.open.kingdee.com/k3cloud/");
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
        queryParam.setFormId("BD_Material");
        queryParam.setFieldKeys("FMATERIALID,FNumber,FName,FCreateOrgId,FUseOrgId");
        List<Md> result = k3CloudApi.executeBillQuery(queryParam, Md.class);
        System.out.println("物料单据查询接口: " + result.size());
        for(Md m : result){
            System.out.println(m.toString());
        }
    }
}
