package com.benewake.saleordersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.benewake.saleordersystem.entity.Item;
import com.benewake.saleordersystem.mapper.ItemMapper;
import com.benewake.saleordersystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lcs
 * @since 2023年07月07 18:20
 * 描 述： TODO
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Item> itemCodeLikeList(String itemCode) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("item_code,item_name");
        queryWrapper.like("item_code",itemCode);
        return itemMapper.selectList(queryWrapper);
    }
}
