package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.entity.Customer;
import com.benewake.saleordersystem.service.CustomerService;
import com.benewake.saleordersystem.service.CustomerTypeService;
import com.benewake.saleordersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Lcs
 * @since 2023年07月12 11:15
 * 描 述： TODO
 */
@Controller
@ResponseBody
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerTypeService customerTypeService;

    @PostMapping("/likeList")
    public Result<List<Customer>> getCustomerLikeList(String customerName){
        return Result.success(customerService.getCustomerLikeList(customerName));
    }

    @PostMapping("/type")
    public Result<String> getCustomerType(Long itemId,Long customerId){
        return Result.success(customerTypeService.getCustomerTypeByRule(customerId,itemId));
    }

}
