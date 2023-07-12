package com.benewake.saleordersystem.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.benewake.saleordersystem.entity.Inquiry;
import com.benewake.saleordersystem.model.InquiryModel;
import com.benewake.saleordersystem.service.InquiryService;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lcs
 * @since 2023年07月09 10:14
 * 描 述： TODO
 */
@Slf4j
@Data
public class InquiryExcelListener extends AnalysisEventListener<InquiryModel> implements BenewakeConstants {

    private List<Inquiry> lists = new ArrayList<>();
    private Map<String,Object> map = new HashMap<>();
    private InquiryService inquiryService;

    public InquiryExcelListener(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    private static List<String> head = new ArrayList<>();
    static {
        head.add("订单类型");
        head.add("物料编码");
        head.add("物料名称");
        head.add("数量");
        head.add("客户名称");
        head.add("销售员");
        head.add("产品类型");
        head.add("客户类型");
        head.add("期望发货日期");
        head.add("计划反馈日期");
        head.add("是否延期");
        head.add("备注");
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        // 判断列名是否一致
        for(int i=0;i<head.size(); i++){
            String h;
            if((h = headMap.get(i))==null || !h.equals(head.get(i))){
                map.put("error","第"+(i+1)+"列的列名不符合条件，应改为:"+head.get(i));
                log.error("第"+(i+1)+"列的列名不符合条件，应改为:"+head.get(i));
                throw new ExcelAnalysisStopException();
            }
        }
//        headMap.forEach((k,v)->{
//            System.out.print(k+":"+v+" ");
//            System.out.println();
//        });
    }

    @Override
    public void invoke(InquiryModel inquiryModel, AnalysisContext analysisContext) {
        log.info("解析到一条数据："+inquiryModel.toString());
        ReadRowHolder readRowHolder = analysisContext.readRowHolder();
        Integer rowIndex = readRowHolder.getRowIndex();
        map = inquiryService.checkAddByExcel(inquiryModel,rowIndex);
        if(!map.containsKey("inquiry")){
            throw new ExcelAnalysisStopException();
        }
        Inquiry inquiry = (Inquiry) map.get("inquiry");
        map.remove("inquiry");
        lists.add(inquiry);
        log.info("第"+rowIndex+"行添加完成: "+inquiry.toString());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析完成,开始加入数据库");
        try {
            inquiryService.insertLists(lists);
        }catch (Exception e) {
            e.printStackTrace();
            map.put("errot","持久化失败");
            throw new ExcelAnalysisException("持久化失败");
        }
        map.put("success","全部数据导入成功！");
    }


}
