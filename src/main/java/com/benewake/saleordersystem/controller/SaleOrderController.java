package com.benewake.saleordersystem.controller;

import com.benewake.saleordersystem.annotation.LoginRequired;
import com.benewake.saleordersystem.entity.Inquiry;
import com.benewake.saleordersystem.entity.User;
import com.benewake.saleordersystem.entity.VO.FilterCriteria;
import com.benewake.saleordersystem.entity.VO.FilterVo;
import com.benewake.saleordersystem.entity.VO.StartInquiryVo;
import com.benewake.saleordersystem.entity.View;
import com.benewake.saleordersystem.entity.ViewCol;
import com.benewake.saleordersystem.service.DeliveryService;
import com.benewake.saleordersystem.service.InquiryService;
import com.benewake.saleordersystem.service.ViewColService;
import com.benewake.saleordersystem.service.ViewService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import com.benewake.saleordersystem.utils.HostHolder;
import com.benewake.saleordersystem.utils.Result;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    private DeliveryService deliveryService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private ViewColService viewColService;
    @Autowired
    private ViewService viewService;

    /**
     * 已登录用户根据tableid获取对应的新增视图，若无新增视图则为空
     * @return
     */
    @PostMapping("/views")
    @LoginRequired
    public Result<List<View>> getViewByTableId(@RequestBody Map<String,Object> param){
        Long tableId = Long.parseLong((String) param.get("tableId"));
        User u = hostHolder.getUser();
        List<View> lists = viewService.getUserView(u.getId(),tableId);

        return Result.success(lists);
    }
    @PostMapping("/cols")
    public Result<Map<String,Object>> getAllCols(@RequestBody Map<String,Object> param){
        Long tableId = Long.parseLong((String) param.get("tableId"));
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> maps = viewService.getAllCols(tableId);
        map.put("cols",maps);
        return Result.success(map);
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
        if(filterVo==null || filterVo.getTableId()==null || filterVo.getViewId()==null){
            res.put("error","参数错误！");
            return Result.fail(res);
        }
        // 当前登录用户
        User loginUser = hostHolder.getUser();
        if(filterVo.getViewId() <= 0){
            // 我的视图
            // 列信息
            List<Map<String,Object>> cols = viewColService.getCols(filterVo.getTableId(), filterVo.getViewId(), loginUser.getUserType().equals(1L));
            // 查看我的
            List<Map<String,Object>> lists;
            if(loginUser.getUserType().equals(1L) || (filterVo.getTableId().equals(1L)&&filterVo.getViewId().equals(-1L))){
                // 管理员 或系统全部
                lists = inquiryService.selectSalesOrderVoList(filterVo.getFilterCriterias(),null);
            }else{
                // 普通用户
                lists = inquiryService.selectSalesOrderVoList(filterVo.getFilterCriterias(),loginUser.getUsername());
            }
            res.put("lists",lists);
            res.put("cols",cols);
        }else{
            // 个人设定的视图
            // 列信息
            List<Map<String,Object>> cols = viewColService.getCols(filterVo.getTableId(),filterVo.getViewId(),loginUser.getUserType().equals(1L));
            List<FilterCriteria> filters = filterVo.getFilterCriterias()==null?new ArrayList<>():filterVo.getFilterCriterias();
            // 添加方案默认筛选信息
            for(Map<String,Object> col : cols){
                String colValue = (String) col.get("col_value");
                if(!StringUtils.isEmpty(colValue)){
                    filters.add(new FilterCriteria((String) col.get("col_name_ENG"),EQUAL,colValue));
                }
            }
            // 根据筛选条件获取信息
            List<Map<String,Object>> lists = inquiryService.selectSalesOrderVoList(filters, loginUser.getUsername());

            res.put("lists",lists);
            res.put("cols",cols);
        }
        return Result.success(res);
    }

    @PostMapping("/saveView")
    @LoginRequired
    @Transactional
    public Result<String> savePlan(@RequestBody FilterVo filterVo){
        if(filterVo.getTableId() == null){
            return Result.fail("表id不能为空！");
        }
        if(CollectionUtils.isEmpty(filterVo.getCols())){
            return Result.fail("新增方案的列信息不能为空！");
        }
        // 获取当前用户
        User u = hostHolder.getUser();
        // 持久化视图
        View view = new View();
        view.setTableId(filterVo.getTableId());
        view.setViewName(filterVo.getViewName());
        view.setUserId(u.getId());
        viewService.saveView(view);

        List<ViewCol> cols = filterVo.getCols();
        for(ViewCol vc : cols){
            vc.setViewId(view.getViewId());
        }
        viewColService.saveCols(cols);

        return Result.success("方案添加成功！");
    }

    /**
     * 保存询单信息
     * @return
     */
    @PostMapping("/save")
    @LoginRequired
    @Transactional
    public Result<Map<String,Object>> addInquiries(@RequestBody StartInquiryVo param){
        List<Inquiry> newInquiries = param.getInquiryList();
        Integer startInquiry = param.getStartInquiry();
//        for(Inquiry inquiry : newInquiries){
//            System.out.println(inquiry.toString());
//        }
        Map<String,Object> map = new HashMap<>();
        if(newInquiries == null){
            map.put("failMsg","请添加至少一条询单信息");
            return Result.fail(map);
        }
        if(newInquiries.get(0).getInquiryCode()==null){
            User user = hostHolder.getUser();
            Date nowTime = new Date();
            if(newInquiries.get(0).getInquiryType()==null){
                map.put("failMsg","请选择订单类型");
                return Result.fail(map);
            }
            // 获取新增的订单编码
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
                inq.setState(0);

                System.out.println(inq.toString());
            }
            map.put("successMsg",inquiryCodeList.get(0));
            // 添加运输信息
            deliveryService.insertLists(newInquiries);
            // 全部通过加入数据库
            inquiryService.insertLists(newInquiries);
        }
        // 询单
        if(startInquiry!=null && startInquiry != 0){
            List<String> fail = new ArrayList<>();
            List<Inquiry> success = new ArrayList<>();
            int ind = 1;
            for(int i=0;i<newInquiries.size();++i){
                Inquiry inquiry = newInquiries.get(i);
                if(inquiry.getInquiryType().equals(ITEM_TYPE_MATERIALS_AND_SOFTWARE_BESPOKE) ||
                        inquiry.getInquiryType().equals(ITEM_TYPE_RAW_MATERIALS_BESPOKE) || inquiry.getSaleNum()<=0){
                    fail.add(String.valueOf(ind));
                }else{
                    success.add(inquiry);
                }
            }
            if(fail.size() > 0){
                map.put("fail","序号"+String.join(",",fail)+"。请飞书联系计划！");
            }
            if(success.size() > 0){
                map.put("success",success.size()+"个订单开始询单！");

                // 询单功能（待添加)   异步

                // 设置state+1
            }
        }


        return Result.success(map);
    }

    /**
     * 检查新增订单是否有效
     * @param inquiry
     * @return
     */
    private boolean checkInquiry(Inquiry inquiry) {
        return true;

    }

//    @PostMapping("/startInquiry")
//    @LoginRequired
//    @Deprecated
//    public Result<Map<String,Object>> startInquiry(@RequestBody Map<String,List<StartInquiryVo>> request){
//        Map<String,Object> map = new HashMap<>();
//        List<StartInquiryVo> startInquiries = request.getOrDefault("inquiries",new ArrayList<>());
//        List<String> fail = new ArrayList<>();
//        List<Long> success = new ArrayList<>();
//        for(int i=0;i<startInquiries.size(); i++){
//            Integer type = startInquiries.get(i).getItemType();
//            Integer num = startInquiries.get(i).getSaleNum();
//            // 超出数量限制逻辑还未确定 暂时用 num<0占位
//            if(type.equals(ITEM_TYPE_MATERIALS_AND_SOFTWARE_BESPOKE) || type.equals(ITEM_TYPE_RAW_MATERIALS_BESPOKE) || num < 0){
//                fail.add(String.valueOf(1+i));
//            }else{
//                success.add(startInquiries.get(i).getInquiryId());
//            }
//        }
//        if(fail.size() > 0){
//            map.put("fail","序号"+String.join(",",fail)+"。请飞书联系计划！");
//        }
//        if(success.size() > 0){
//            map.put("success",success.size()+"个订单开始询单！");
//
//            // 询单功能（待添加)
//
//        }
//        return Result.success(map);
//    }


    @PostMapping("/delete")
    @LoginRequired
    public Result<Map<String, Object>> deleteOrder(@RequestBody Map<String,Long> param){
        Long orderId = param.get("orderId");
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
        if(file==null){
            map.put("error","文件为空！");
            return Result.fail(map);
        }
        val split = file.getOriginalFilename().split("\\.");
        if(!"xlsx".equals(split[1]) && !"xls".equals(split[1])){
            map.put("error","请提供.xlsx或.xls为后缀的Excel文件");
            return Result.fail(map);
        }
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
