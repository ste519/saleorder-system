package com.benewake.saleordersystem;

import com.benewake.saleordersystem.model.SaleOut;
import com.kingdee.bos.webapi.entity.QueryParam;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lcs
 * @since 2023年07月13 10:34
 * 描 述： TODO
 */
@Service
public class SaleOut1 {

    @Autowired
    private K3CloudApi api;

    public <T> List<T> searchData(String formId, List<String> fieldKeys, String queryFilters, int limit, Class type) throws Exception {
        List<T> res = new ArrayList<>();
        int offset = 0;
        do{
            int lim = Math.min(10000,limit);
            int size = res.size();
            QueryParam queryParam = new QueryParam();
            queryParam.setFormId(formId)
                    .setFieldKeys(String.join(",",fieldKeys))
                    .setFilterString(queryFilters)
                    .setLimit(lim)
                    .setStartRow(offset);
            res.addAll(api.executeBillQuery(queryParam, type));
            if(res.size() == size) break;
            offset += lim;
            limit -= 10000;
        }while (limit>0);
        return res;
    }

    public List<SaleOut> searchSaleOutList(String queryFilters, int limit) throws Exception {
        String formId = "SAL_OUTSTOCK";
        List<String> fieldKeys = new ArrayList<>();
        fieldKeys.add("FBillNo");
        fieldKeys.add("FMaterialID");
        fieldKeys.add("FMaterialName");
        fieldKeys.add("FRealQty");
        //fieldKeys.add("FStockOrgId");
        fieldKeys.add("FCustomerID");
        fieldKeys.add("FSalesManID");
        fieldKeys.add("FDate");
        fieldKeys.add("FSoorDerno");
        fieldKeys.add("FCarriageNO");
        List<SaleOut> lists = searchData(formId,fieldKeys,queryFilters,limit, SaleOut.class);
        return lists;
    }

    public List<SaleOut> searchSaleOutList1(int limit) throws Exception {
        List<String> queryFilters = new ArrayList<>();
        // 发货组织为北醒(北京)光子科技有限公司
        queryFilters.add("FStockOrgId = 1");
        // 客户不为北醒（北京）商贸有限公司
        queryFilters.add("FCustomerID != 104169");
        // 客户不为北京北醒智能设备有限公司
        queryFilters.add("FCustomerID != 458103");
        return searchSaleOutList(String.join(" and ",queryFilters),limit);
    }
}
