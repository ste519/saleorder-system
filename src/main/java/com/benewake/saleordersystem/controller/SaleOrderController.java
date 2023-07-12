package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
import com.benewake.saleordersystem.entity.Inquiry;
import com.benewake.saleordersystem.entity.Item;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.entity.VO.FilterVo;
import com.benewake.saleordersystem.entity.View;
import com.benewake.saleordersystem.service.InquiryService;
import com.benewake.saleordersystem.service.ItemService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import com.benewake.saleordersystem.utils.HostHolder;
import com.benewake.saleordersystem.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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
public class SaleOrderController implements BenewakeConstants {

    @Autowired
    private InquiryService inquiryService;
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private ItemService itemService;

    @GetMapping("/table/{tableId}")
    @LoginRequired
    public Result<List<View>> getViewByTableId(@Param("tableId")Long tableId){


        return null;
    }

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
        res.put("list",inquiryService.selectSalesOrderVoList(filterVo.getFilterCriterias(),filterVo.getTableId()==1?null:hostHolder.getUser().getUsername()));
        return Result.success();
    }

    @GetMapping("/savePlan")
    @LoginRequired
    public Result<Map<String,Object>> savePlan(){

        return Result.success();
    }

    /**
     * 保存询单信息
     * @param reqMap 待添加的询单列表map
     * @return
     */
    @PostMapping("/add")
    @LoginRequired
    public Result<Map<String,Object>> addInquiries(@RequestBody Map<String,List<Inquiry>> reqMap){
        List<Inquiry> newInquiries = reqMap.get("newInquiries");

        Map<String,Object> map = new HashMap<>();
        if(newInquiries == null){
            map.put("failMsg","请添加至少一条询单信息！");
            return Result.fail(map);
        }
        User user = hostHolder.getUser();
        Date nowTime = new Date();
        // 逐条分析询单是否合法
        for(Inquiry inq : newInquiries){
            Map<String,Object> res = inquiryService.addValid(inq);
            if(res.size()>0){
                return Result.fail(res);
            }
            // 设置创建人信息以及单据编号
            inq.setCreatedTime(nowTime);
            inq.setCreatedUser(user.getId());
            inq.setInquiryCode(inquiryService.getDocumentNumberFormat(inq.getInquiryType()));

            System.out.println(inq.toString());
        }

        // 全部通过加入数据库
        map.put("successMsg","保存成功！");

        return Result.success(map);
    }

    @PostMapping("/delete")
    @LoginRequired
    public Result<Map<String, Object>> deleteOrder(@RequestParam Map<String,Long> request){
        Long orderId = request.get("orderId");
        System.out.println(orderId);
        boolean res = inquiryService.deleteOrder(orderId);
        Map<String,Object> map = new HashMap<>();
        if(!res){
            map.put("error","订单不存在！");
            return Result.fail(map);
        }else {
            map.put("success","删除成功！");
            return Result.success(map);
        }
    }

    @PostMapping("/importExcel")
    @LoginRequired
    public Result<Map<String, Object>> addOrdersByExcel(@RequestParam("file")MultipartFile file){
        Map<String,Object> map = new HashMap<>();
        try {
            map = inquiryService.saveData(file);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(map.containsKey("error")){
            return Result.fail(map);
        }else{
            return Result.success(map);
        }
    }
}
