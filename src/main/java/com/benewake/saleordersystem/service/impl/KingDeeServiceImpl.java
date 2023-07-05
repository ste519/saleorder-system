package com.benewake.saleordersystem.service.impl;

import com.benewake.saleordersystem.service.KingDeeService;
import com.kingdee.bos.webapi.entity.QueryParam;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lcs
 * @since 2023年07月04 11:23
 * 描 述： TODO
 */
@Service
public class KingDeeServiceImpl implements KingDeeService {

    @Autowired
    private K3CloudApi api;
    @Override
    public <T> List<T> searchData(String formId, List<String> fieldKeys, String queryFilters, int offset,int limit,Class type) throws Exception {
        QueryParam queryParam = new QueryParam();
        queryParam.setFormId(formId)
                .setFieldKeys(String.join(",",fieldKeys))
                .setFilterString(queryFilters)
                .setLimit(limit)
                .setStartRow(offset*limit);
        List<T> res = api.executeBillQuery(queryParam, type);
        return res;
    }
}
