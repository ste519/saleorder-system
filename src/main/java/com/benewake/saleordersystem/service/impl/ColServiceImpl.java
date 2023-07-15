package com.benewake.saleordersystem.service.impl;

import com.benewake.saleordersystem.entity.Col;
import com.benewake.saleordersystem.mapper.ColMapper;
import com.benewake.saleordersystem.service.ColService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月12 14:33
 * 描 述： TODO
 */
@Service
public class ColServiceImpl implements ColService {

    @Autowired
    private ColMapper colMapper;

    private static final List<Map<String,Object>> MY_MAPS;
    private static final List<Map<String,Object>> ALL_MAPS;
    static {
        String[] engs = {"salesman_name","inquiry_code","inquiry_init_type","state","item_code","item_name",
        "sale_num","customer_name","inquiry_type","item_type","customer_type","expected_time",
        "arranged_time","delay","remark"};
        String[] cns = {"销售员","单据编号","单据类型","单据状态","物料编码","物料名称","数量","客户名称","订单状态",
                "产品类型","客户类型","期望发货日期","计划反馈日期","是否延期","备注"};
        MY_MAPS = new ArrayList<>();
        for(int i=1;i<engs.length;++i){
            Map<String,Object> map = new HashMap<>();
            map.put("col_name_ENG",engs[i]);
            map.put("col_name_CN",cns[i]);
            map.put("col_seq",i);
            MY_MAPS.add(map);
        }
        ALL_MAPS = new ArrayList<>();
        for(int i=0;i< engs.length;++i){
            Map<String,Object> map = new HashMap<>();
            map.put("col_name_ENG",engs[i]);
            map.put("col_name_CN",cns[i]);
            map.put("col_seq",i+1);
            ALL_MAPS.add(map);
        }
    }

    @Override
    public List<Map<String,Object>> getCols(Long viewId, boolean isAdmin) {
        if(viewId == -1 || viewId == 0){
            // 通用方案视图
            if(isAdmin || viewId == 0) {
                return ALL_MAPS;
            } else {
                return MY_MAPS;
            }
        }else {
            // 个人方案视图
            return colMapper.getColMaps(viewId);
        }
    }
}
