package com.benewake.saleordersystem.service;


import com.benewake.saleordersystem.entity.sfexpress.SF_SEARCH_RESULT;
import com.benewake.saleordersystem.utils.Result;


/**
 * @author Lcs
 * 描述：顺丰查询信息接口
 */
public interface SFExpressService {

    SF_SEARCH_RESULT findRoutesByCode(String code, String tel);

    SF_SEARCH_RESULT searchPromitm(String code, String tel);
}
