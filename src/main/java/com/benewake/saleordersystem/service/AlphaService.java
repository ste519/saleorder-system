package com.benewake.saleordersystem.service;

import com.benewake.saleordersystem.entity.FilterCriteria;
import com.benewake.saleordersystem.entity.Past.PastOrder;

import java.util.List;
import java.util.Map;

public interface AlphaService {

    void alphaPython();

    List<PastOrder> getList(List<FilterCriteria> filters);

}
