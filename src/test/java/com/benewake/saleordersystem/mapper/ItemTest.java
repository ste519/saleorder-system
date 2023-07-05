package com.benewake.saleordersystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.benewake.saleordersystem.SaleOrderSystemApplication;
import com.benewake.saleordersystem.entity.Item;
import com.benewake.saleordersystem.entity.Past.PastItemChange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

/**
 * @author Lcs
 * @since 2023年06月29 17:24
 * 描 述： TODO
 */
@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class ItemTest {

    @Autowired
    MybatisPlusInterceptor mybatisPlusInterceptor;
    @Autowired
    PastItemChangeMapper pastItemChangeMapper;

    @Autowired
    ItemMapper itemMapper;

    @Test
    public void testPage(){
        // 分页测试
        Page<PastItemChange> page = new Page<>(1,4);
        IPage<PastItemChange> iPage = pastItemChangeMapper.selectPage(page,null);
        System.out.println(iPage.getPages());
        System.out.println(iPage.getTotal());
        for(PastItemChange p : iPage.getRecords()){
            System.out.println(p.toString());
        }
    }

    @Test
    public void testItem(){
        itemMapper.selectList(null).forEach(System.out::println);
    }


}
