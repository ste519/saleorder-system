package com.benewake.saleordersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.benewake.saleordersystem.entity.Item;
import com.benewake.saleordersystem.mapper.ItemMapper;
import com.benewake.saleordersystem.service.ItemService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月07 18:20
 * 描 述： TODO
 */
@Service
public class ItemServiceImpl implements ItemService , BenewakeConstants {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Map<String, Object>> itemCodeLikeList(String itemCode) {
        return itemMapper.selectCodeLikeList("%"+itemCode+"%");
    }

    @Override
    public Item findItemById(Long itemId) {
        return itemMapper.selectById(itemId);
    }

    @Override
    public Item findItemByCode(String itemCode) {
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Item::getId,Item::getItemName,Item::getItemType)
                .eq(Item::getItemCode,itemCode);
        return itemMapper.selectOne(queryWrapper);
    }

    @Override
    public int transferItemType(String itemType) {
        switch (itemType){
            case "已有竞品":
                return 1;
            case "已有定制":
                return 2;
            case "软件定制":
                return 3;
            case "原材料定制":
                return 4;
            default:
                return -1;
        }
    }
}
