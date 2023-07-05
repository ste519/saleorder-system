package com.benewake.saleordersystem.service.impl;

import com.benewake.saleordersystem.entity.Customer;
import com.benewake.saleordersystem.mapper.CustomerMapper;
import com.benewake.saleordersystem.service.CustomerService;
import com.kingdee.bos.webapi.entity.QueryParam;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lcs
 * @since 2023年07月03 17:12
 * 描 述： TODO
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private K3CloudApi api;

    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public int updateCustomerDB() {
        // 查询条件
//        QueryParam queryParam = new QueryParam();
//        queryParam.setFormId("BD_Customer");
//        queryParam.setFieldKeys("FCustId,FName");
//        int ans = 0;
//        try {
//            List<Customer> res = api.executeBillQuery(queryParam,Customer.class);
//            ans = customerMapper.updateCustomer(res);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return ans;
        return 0;
    }
}
