package com.benewake.saleordersystem.mapper.Vo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 */
@Mapper
public interface SalesOrderVoMapper extends BaseMapper<Map<String,Object>> {
    @Select("<script>" +
            "select * from fim_item_table " +
            "${ew.customSqlSegment} " +
            "limit #{offset},#{limit} " +
            "</script>")
    List<Map<String,Object>> getALL(@Param("ew") Wrapper wrapper,@Param("offset") int offset,@Param("limit") int limit);

    @Select("<script>" +
            "select inquiry_id,created_user_name,inquiry_code,item_type,inquiry_type,item_code,item_name," +
            "customer_name,salesman_name,sale_num,expected_time,arranged_time,delay,customer_type,remark " +
            "from " +
            "(select inquiry_id,inquiry_code,inquiry_type,sale_num,expected_time,arranged_time,created_user,salesman_id," +
            "item_id,customer_id,(case when arranged_time > expected_time then '是' else '否' end) as delay,remark " +
            "from fim_inquiry_table " +
            "${qw1.customSqlSegment}" +
            ") as a " +
            "left join " +
            "(select FIM_user_id,FIM_user_name as salesman_name " +
            "from fim_users_table " +
            "${qw2.customSqlSegment} " +
            " ) as b " +
            "on a.salesman_id = b.FIM_user_id " +
            "left join " +
            "(select FIM_user_id,FIM_user_name as created_user_name " +
            "from fim_users_table " +
            "${qw3.customSqlSegment} " +
            " ) as c " +
            "on a.created_user = c.FIM_user_id " +
            "left join " +
            "(select customer_id,item_id,customer_type " +
            " from fim_customer_type_table " +
            "${qw4.customSqlSegment} " +
            " ) as d " +
            "on a.customer_id = d.customer_id and d.item_id = a.item_id " +
            "left join " +
            "(select item_id,item_code,item_name,item_type " +
            "from fim_item_table " +
            "${qw5.customSqlSegment} " +
            " ) as e " +
            "on e.item_id = a.item_id " +
            "left join " +
            "(select customer_id,customer_name " +
            "from fim_customer_table " +
            "${qw6.customSqlSegment} " +
            " ) as f " +
            "on a.customer_id = f.customer_id " +
            "limit #{offset},#{limit}" +
            "</script>")
    List<Map<String,Object>> selectListByFilter(@Param("qw1")Wrapper wrapper1,@Param("qw2")Wrapper wrapper2,@Param("qw3")Wrapper wrapper3,
                                                @Param("qw4")Wrapper wrapper4,@Param("qw5")Wrapper wrapper5,@Param("qw6")Wrapper wrapper6,
                                                int offset,int limit);
}
