package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.service.InquiryService;
import com.benewake.saleordersystem.utils.HostHolder;
import com.benewake.saleordersystem.utils.Result;
import jnr.ffi.annotations.In;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/allList")
    @LoginRequired
    public Result<Map<String,Object>> selectList(HttpServletRequest request,
                                                 HttpServletResponse response, Integer tableId, Integer plan, Integer offset, Integer limit,
                                                 String matp){
        // 当前登录用户
        User loginUser = hostHolder.getUser();

        //String ma_tp = (String) request.getAttribute("matp");
        String ma_va = (String) request.getAttribute("mava");
        System.out.println(matp+" "+ma_va);
        return Result.success();
    }

}
