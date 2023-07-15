package com.benewake.saleordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benewake.saleordersystem.entity.Col;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ColMapper extends BaseMapper<Col> {

    /**
     * 根据viewId获取相应的列信息
     * @param viewId
     * @return
     */
    @Select({"call fim_aps_system.get_view_col(#{viewId,mode=IN,jdbcType=BIGINT})"})
    List<Map<String,Object>> getColMaps(@Param("viewId")Long viewId);
}
