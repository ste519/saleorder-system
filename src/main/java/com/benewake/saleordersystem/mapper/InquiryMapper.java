package com.benewake.saleordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benewake.saleordersystem.entity.Inquiry;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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
    @Options(useGeneratedKeys = true,keyProperty = "inquiryId")
    int insertInquiries(@Param("lists") List<Inquiry> lists);

    /**
     * 获取订单中po pr yg状态对应的物料库存情况
     * @return
     */
    @Select("<script>" +
            "select b.item_code,b.item_name,sum(case when inquiry_type=1 then sale_num else 0 end) as 'poCount'," +
            "sum(case when inquiry_type = 2 then sale_num else 0 end) as 'prCount', " +
            "sum(case when inquiry_type = 3 then sale_num else 0 end) as 'ygCount'" +
            "from fim_inquiry_table as a left join fim_item_table as b on a.item_id = b.item_id  " +
            "group by a.item_id " +
            "</script>")
    List<Map<String,Object>> getItemNum();

}
