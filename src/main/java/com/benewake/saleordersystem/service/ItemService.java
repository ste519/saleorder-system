package com.benewake.saleordersystem.service;

import com.benewake.saleordersystem.entity.Item;

import java.util.List;

/**
 * @author Lcs
 */
public interface ItemService {

    /**
     * 根据物料编码模糊查询物料
     * @param itemCode
     * @return
     */
    List<Item> itemCodeLikeList(String itemCode);

}
