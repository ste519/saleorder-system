package com.benewake.saleordersystem.service;

import com.benewake.saleordersystem.entity.Inquiry;

import java.util.List;

/**
 * @author Lcs
 */
public interface DeliveryService {

    /**
     * 获取运输单号并更新订单运输路由状态
     * @return
     */
    boolean updateDelivery();

    int insertLists(List<Inquiry> lists);
}
