package com.benewake.saleordersystem.service;

import java.util.List;
import java.util.Map;

public interface ColService {

    /**
     * 根据视图id获取视图对应的列信息
     * @param viewId
     * @param isAdmin 是否是管理员
     * @return
     */
    List<Map<String,Object>> getCols(Long viewId, boolean isAdmin);

}
