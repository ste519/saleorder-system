package com.benewake.saleordersystem.service;

import com.benewake.saleordersystem.utils.Result;

/**
 * @author Lcs
 */
public interface CustomerService {

    /**
     * 从金蝶取数并更新用户id->用户名称映射表
     * @return
     */
    @Deprecated
    int updateCustomerDB();

}
