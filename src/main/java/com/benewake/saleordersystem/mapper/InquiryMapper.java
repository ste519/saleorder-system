package com.benewake.saleordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benewake.saleordersystem.entity.Inquiry;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Lcs
 */
@Mapper
public interface InquiryMapper extends BaseMapper<Inquiry> {

    /**
     * 批量添加询单信息
     * @param lists
     * @return
     */
    @Insert("<script> " +
            "Insert into fim_inquiry_table(inquiry_code,state,created_time," +
            "created_user,salesman_id,item_id,customer_id,sale_num,expected_time,arranged_time,inquiry_type,remark) " +
            "values " +
            "<foreach collection='lists' item='list' separator=','> " +
            "(" +
            "#{list.inquiryCode},#{list.state},#{list.createdTime},#{list.createdUser}," +
            "#{list.salesmanId},#{list.itemId},#{list.customerId},#{list.saleNum}," +
            "#{list.expectedTime},#{list.arrangedTime},#{list.inquiryType},#{list.remark} " +
            ")" +
            "</foreach>" +
            "</script>")
    int insertInquiries(@Param("lists") List<Inquiry> lists);


}
