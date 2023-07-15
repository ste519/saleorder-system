package com.benewake.saleordersystem.service;

import com.benewake.saleordersystem.entity.View;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月12 13:53
 * 描 述： TODO
 */
public interface ViewService {

    List<View> getUserView(Long userId,Long tableId);

}
