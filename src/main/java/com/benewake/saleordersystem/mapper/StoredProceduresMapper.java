package com.benewake.saleordersystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * 描 述：调用存储过程 返回值统一为Map<String,Object> 只需读不需要修改
 */
@Mapper
public interface StoredProceduresMapper extends BaseMapper {

    @Select({"call past_orders_analysis_1_salesman_selling_condition()"})
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String,Object>> doPastOrdersAnalysis1SalesmanSellingCondition();

    @Select({"call past_orders_analysis_2_quarterly_selling_condition()"})
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String,Object>> doPastOrdersAnalysis2QuarterlySellingCondition();

    @Select({"call past_orders_analysis_3_month_avg_proportion()"})
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String,Object>> doPastOrdersAnalysis3MonthAvgProportion();

    @Select({"call past_orders_analysis_4_temp2_eligible_orders()"})
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String,Object>> doPastOrdersAnalysis4Temp2EligibleOrders();

    @Select({"call past_orders_analysis_5_temp3_not_eligible_orders()"})
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String,Object>> doPastOrdersAnalysis5Temp3NotEligibleOrders();

    @Select({"call past_orders_analysis_6_salesman_selling_condition_replaced()"})
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String,Object>> doPastOrdersAnalysis6SalesmanSellingConditionReplaced();

    @Select({"call past_orders_analysis_7_quarterly_selling_condition_replaced()"})
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String,Object>> doPastOrdersAnalysis7QuarterlySellingConditionReplaced();

    @Select({"call past_orders_analysis_8_major_customers_situation()"})
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String,Object>> doPastOrdersAnalysis8MajorCustomersSituation();

}
