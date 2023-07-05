package com.benewake.saleordersystem.service;

import java.util.List;

/**
 * @author Lcs
 */
public interface KingDeeService {

    /**
     * 从金蝶星空云中获取数据
     * @param formId 表单ID
     * @param fieldKeys 查询的字段
     * @param queryFilters 过滤条件
     * @param offset 起始页数
     * @param limit 每页查询数量
     * @param type 返回的数据对象
     * @return
     * @param <T>
     * @throws Exception
     */
    <T> List<T> searchData(String formId, List<String> fieldKeys, String queryFilters,int offset ,int limit,Class type) throws Exception;
}
