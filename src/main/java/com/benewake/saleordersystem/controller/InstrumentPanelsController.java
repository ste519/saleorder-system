package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.entity.Past.PastOrder;
import com.benewake.saleordersystem.service.KingDeeService;
import com.benewake.saleordersystem.service.PastOrderService;
import com.benewake.saleordersystem.utils.Result;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lcs
 * @since 2023年07月15 15:54
 * 描 述： TODO
 */
@Controller
@ResponseBody
@RequestMapping("/instrument")
public class InstrumentPanelsController {

    @Autowired
    private KingDeeService kingDeeService;
    @Autowired
    private PastOrderService pastOrderService;
    @GetMapping("/test")
    public Result<String> getTest(){

//        try {
//            val saleOuts1 = kingDeeService.searchSaleOutList1(Integer.MAX_VALUE);
//            saleOuts1.addAll(kingDeeService.searchSaleOutList2(Integer.MAX_VALUE));
//            val withdraws1 = kingDeeService.searcWithdrawList1(Integer.MAX_VALUE);
//            withdraws1.addAll(kingDeeService.searcWithdrawList2(Integer.MAX_VALUE));
//
//            List<PastOrder> list = new ArrayList<>();
//            list.addAll(pastOrderService.transferSaleOutToPastOrder(saleOuts1));
//            list.addAll(pastOrderService.transferWithdrawToPastOrder(withdraws1));
//
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        return Result.success();
    }

}
