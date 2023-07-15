package com.benewake.saleordersystem.entity.VO;

import com.benewake.saleordersystem.entity.Col;
import com.benewake.saleordersystem.entity.FilterCriteria;
import lombok.Data;

import java.util.List;

/**
 * @author Lcs
 * @since 2023年07月06 15:48
 * 描 述： 查询列表筛选视图信息
 */
@Data
public class FilterVo {
    Long tableId;
    Long viewId;
    List<FilterCriteria> filterCriterias;
    List<Col> cols;
}
