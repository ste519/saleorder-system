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
public interface SalesOrderVoMapper extends BaseMapper<List<Map<String,Object>>> {
    @Select("<script>" +
            "select * from fim_item_table " +
            "${ew.customSqlSegment} " +
            "</script>")
    List<Map<String,Object>> getALL(@Param("ew") Wrapper wrapper);
}
