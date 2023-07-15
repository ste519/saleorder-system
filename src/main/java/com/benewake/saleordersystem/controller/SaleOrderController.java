package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
import com.benewake.saleordersystem.entity.FilterCriteria;
import com.benewake.saleordersystem.entity.Inquiry;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.entity.VO.FilterVo;
import com.benewake.saleordersystem.entity.View;
import com.benewake.saleordersystem.service.ColService;
import com.benewake.saleordersystem.service.InquiryService;
import com.benewake.saleordersystem.service.ViewService;
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
    private ColService colService;
    @Autowired
    private ViewService viewService;

    /**
     * 已登录用户根据tableid获取对应的新增视图，若无新增视图则为空
     * @param tableId
     * @return
     */
    @GetMapping("/view")
    @LoginRequired
    public Result<List<View>> getViewByTableId(@Param("tableId")Long tableId){
        User u = hostHolder.getUser();
        List<View> lists = viewService.getUserView(u.getId(),tableId);

        return Result.success(lists);
    }

    /**
     * 1-6订单列表过滤查询
     * viewId = -1 表示查看我的  viewId = 0 表示查看全部
     * @param filterVo
     * @return
     */
    @PostMapping("/Lists")
    @LoginRequired
    public Result<Map<String,Object>> selectList(@RequestBody FilterVo filterVo){
        Map<String,Object> res = new HashMap<>();
        // 当前登录用户
        User loginUser = hostHolder.getUser();
        if(filterVo.getViewId() == -1){
            // 我的视图
            // 列信息
            List<Map<String,Object>> cols = colService.getCols(-1L, loginUser.getUserType().equals(1L));
            // 查看我的
            List<Map<String,Object>> lists;
            if(loginUser.getUserType().equals(1L)){
                // 管理员
                lists = inquiryService.selectSalesOrderVoList(filterVo.getFilterCriterias(),null);
            }else{
                // 普通用户
                lists = inquiryService.selectSalesOrderVoList(filterVo.getFilterCriterias(),loginUser.getUsername());
            }
            res.put("lists",lists);
            res.put("cols",cols);

        }else if(filterVo.getViewId() == 0){
            // 全部视图
            // 列信息
            List<Map<String,Object>> cols = colService.getCols(0L,loginUser.getUserType().equals(1L));
            // 查看全部
            List<Map<String,Object>> lists = inquiryService.selectSalesOrderVoList(filterVo.getFilterCriterias(),null);

            res.put("lists",lists);
            res.put("cols",cols);
        }else{
            // 个人设定的视图
            // 列信息
            List<Map<String,Object>> cols = colService.getCols(filterVo.getViewId(),loginUser.getUserType().equals(1L));
            List<FilterCriteria> filters = filterVo.getFilterCriterias();
            // 添加方案默认筛选信息
            cols.forEach(m->{
                String filterValue = (String) m.get("col_value");
                if(filterValue!=null){
                    filters.add(new FilterCriteria((String) m.get("col_name_ENG"),EQUAL,filterValue));
                }
            });
            // 根据筛选条件获取信息
            List<Map<String,Object>> lists = inquiryService.selectSalesOrderVoList(filterVo.getFilterCriterias(), loginUser.getUsername());
            res.put("lists",lists);
            res.put("cols",cols);
        }
        return Result.success(res);
    }

    @GetMapping("/savePlan")
    @LoginRequired
    public Result<Map<String,Object>> savePlan(@RequestBody FilterVo filterVo){

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
        List<String> inquiryCodeList = inquiryService.getDocumentNumberFormat(newInquiries.get(0).getInquiryType(),1);
        // 逐条分析询单是否合法
        for(Inquiry inq : newInquiries){
            Map<String,Object> res = inquiryService.addValid(inq);
            if(res.size()>0){
                return Result.fail(res);
            }
            // 设置创建人信息以及单据编号
            inq.setCreatedTime(nowTime);
            inq.setCreatedUser(user.getId());
            inq.setInquiryCode(inquiryCodeList.get(0));

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

    @GetMapping("/updateDelivery")
    @LoginRequired
    public Result<String> updateDelivery(){



        return Result.success("运输状态更新成功！");
    }
}
