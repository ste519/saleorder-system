package com.benewake.saleordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.benewake.saleordersystem.entity.View;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ViewMapper extends BaseMapper<View> {

    /**
     * 根据用户id和表id获取用户新增的视图
     * @param userId
     * @param tableId
     * @return
     */
    @Select({"call fim_aps_system.get_user_view(#{userId,mode=IN,jdbcType=BIGINT},#{tableId,mode=IN,jdbcType=BIGINT})"})
    List<View> getUserView(@Param("userId")Long userId, @Param("tableId")Long tableId);
}
