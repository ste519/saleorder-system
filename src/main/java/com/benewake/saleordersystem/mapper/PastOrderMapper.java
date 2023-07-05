package com.benewake.saleordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benewake.saleordersystem.entity.Past.PastOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Lcs
 */
@Mapper
public interface PastOrderMapper extends BaseMapper<PastOrder> {

    /**
     * 删除当前所有数据并导入本地历史订单数据
     * 描 述：从金蝶获取最新数据后调用
     */
    @Select("call FIM_past_orders_table()")
    void reloadLocalOrders();
}
