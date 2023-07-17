package com.benewake.saleordersystem.service.impl;

import com.benewake.saleordersystem.entity.View;
import com.benewake.saleordersystem.mapper.ViewMapper;
import com.benewake.saleordersystem.mapper.Vo.SalesOrderVoMapper;
import com.benewake.saleordersystem.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月12 13:55
 * 描 述： TODO
 */
@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    private ViewMapper viewMapper;
    @Override
    public List<View> getUserView(Long userId, Long tableId) {
        return viewMapper.getUserView(userId, tableId);
    }

    @Override
    public int saveView(View view) {
        return viewMapper.insertView(view);
    }

    @Override
    public List<Map<String, Object>> getAllCols(Long tableId) {
        if(tableId==null) return new ArrayList<>();
        return viewMapper.getAllCols(tableId);
    }
}