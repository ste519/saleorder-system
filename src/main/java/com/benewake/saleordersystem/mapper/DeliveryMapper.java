package com.benewake.saleordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benewake.saleordersystem.entity.Delivery;
import com.benewake.saleordersystem.entity.Inquiry;
import jnr.ffi.annotations.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Lcs
 */
@Mapper
public interface DeliveryMapper extends BaseMapper<Delivery> {

    /**
     * 批量更新
     * @param deliveryList
     * @return
     */
    @Update("<script>" +
            "<foreach collection='list' item='item' index='index' separator=';'>" +
            "update delivery_table " +
            "<set>" +
            "delivery_state = #{item.deliveryState} " +
            "<if test='item.receiveTime!=null'> " +
            "receive_time = #{item.receiveTime} " +
            "</if> " +
            "<if test='item.deliveryLastestState!=null'> " +
            "delivery_latest_state = #{item.deliveryLastestState} " +
            "</if> " +
            "</set> " +
            "where inquiry_code = #{item.inquiryCode} " +
            "</foreach> " +
            "</script>")
    int updateDeliveries(List<Delivery> deliveryList);

    @Insert("<script>" +
            "insert into delivery_table(" +
            "inquiry_code" +
            ") values " +
            "<foreach collection='lists' item='list' separator=','> " +
            "(" +
            "#{list.inquiryCode}" +
            ")" +
            "</foreach>" +
            "</script>")
    int insertLists(@Param("lists") List<Inquiry> inquiries);

}
