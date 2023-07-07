package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
import com.benewake.saleordersystem.entity.Col;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.entity.VO.FilterVo;
import com.benewake.saleordersystem.service.InquiryService;
import com.benewake.saleordersystem.utils.HostHolder;
import com.benewake.saleordersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月05 16:18
 * 描 述： TODO
 */
@Controller
@RequestMapping("/order")
@ResponseBody
public class SaleOrderController {

    @Autowired
    private InquiryService inquiryService;
    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/allList")
    @LoginRequired
    public Result<Map<String,Object>> selectList(@RequestBody FilterVo filterVo){
        Map<String,Object> res = new HashMap<>();
        // 当前登录用户
        User loginUser = hostHolder.getUser();
        System.out.println(filterVo.toString());
        //System.out.println(col);
        // 获取列名信息
        res.put("cols",inquiryService.getColMaps(filterVo.getTableId(),filterVo.getPlanId(),loginUser.getId()));
        // 添加默认筛选信息

        // 根据筛选条件获取数据
        res.put("list",inquiryService.testItemFilter(filterVo.getFilterCriterias(), filterVo.getOffset(), filterVo.getLimit()));
        return Result.success();
    }

    @GetMapping("/savePlan")
    @LoginRequired
    public Result<Map<String,Object>> savePlan(){

        return Result.success();
    }

}
