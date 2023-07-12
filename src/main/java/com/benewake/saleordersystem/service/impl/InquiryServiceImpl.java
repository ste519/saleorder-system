package com.benewake.saleordersystem.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.benewake.saleordersystem.entity.*;
import com.benewake.saleordersystem.excel.InquiryExcelListener;
import com.benewake.saleordersystem.mapper.InquiryMapper;
import com.benewake.saleordersystem.mapper.Vo.SalesOrderVoMapper;
import com.benewake.saleordersystem.model.InquiryModel;
import com.benewake.saleordersystem.service.*;
import com.benewake.saleordersystem.utils.BenewakeConstants;
import com.benewake.saleordersystem.utils.CommonUtils;
import com.benewake.saleordersystem.utils.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Lcs
 * @since 2023年07月05 16:19
 * 描 述： TODO
 */
@Service
@Slf4j
public class InquiryServiceImpl implements InquiryService, BenewakeConstants {

    @Autowired
    private InquiryMapper inquiryMapper;
    @Autowired
    private SalesOrderVoMapper salesOrderVoMapper;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerTypeService customerTypeService;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public Map<String, Object> saveData(MultipartFile file) {
        InquiryExcelListener listener = new InquiryExcelListener(this);
        Map<String,Object> map;
        try{
            EasyExcel.read(file.getInputStream(), InquiryModel.class,listener).sheet().headRowNumber(1).doRead();
            map = listener.getMap();
        }catch (Exception e) {
            map = listener.getMap();
            e.printStackTrace();
            log.error((String) map.get("error"));
        }
        return map;
    }

    @Override
    @Transactional
    public int insertLists(List<Inquiry> inquiries) {
        return inquiryMapper.insertInquiries(inquiries);
    }

    @Override
    public List<Map<String,Object>> selectSalesOrderVoList(List<FilterCriteria> filters,String username) {

        // 添加筛选条件
        Map<String,Integer> map = new HashMap<>();
        for(int i=0;i<filters.size();++i){
            map.put(filters.get(i).getColName(),i);
        }

        QueryWrapper<Inquiry> queryWrapper1 = new QueryWrapper<>();
        // 默认最新和有效
        queryWrapper1.isNotNull("bb.inquiry_id");
        List<FilterCriteria> f1 = new ArrayList<>();
        // inquiry_init_type 需要int表示
        String[] str1 = {"inquiry_code","sale_num","expected_time","arranged_time",
                "remark","state","inquiry_init_type"};
        for(String s : str1){
            if(map.containsKey(s)){
                f1.add(filters.get(map.get(s)));
            }
        }
        CommonUtils.addFilters(f1,queryWrapper1);

        QueryWrapper<Inquiry> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.isNotNull("item_code");
        f1 = new ArrayList<>();
        String[] str2 = {"item_code","item_name"};
        for(String s : str2){
            if(map.containsKey(s)){
                f1.add(filters.get(map.get(s)));
            }
        }
        CommonUtils.addFilters(f1,queryWrapper2);

        QueryWrapper<Inquiry> queryWrapper3 = new QueryWrapper<>();
        //queryWrapper3.ge("state",0);
        queryWrapper3.isNotNull("item_code");
        f1 = new ArrayList<>();
        String[] str3 = {"inquiry_code","delivery_code","receive_time","delivery_state"};
        for(String s : str3){
            if(map.containsKey(s)){
                FilterCriteria fc = filters.get(map.get(s));
                if("inquiry_code".equals(s)){
                    fc.setColName("a.inquiry_code");
                }
                f1.add(fc);
            }
        }
        CommonUtils.addFilters(f1,queryWrapper3);

        QueryWrapper<Inquiry> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.isNotNull("FIM_user_name");
        f1 = new ArrayList<>();
        String[] str4 = {"salesman_name"};
        for(String s : str4){
            if(map.containsKey(s)){
                FilterCriteria f = filters.get(map.get(s));
                f.setColName("FIM_user_name");
                f1.add(f);
            }
        }
        CommonUtils.addFilters(f1,queryWrapper4);

        QueryWrapper<Inquiry> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.isNotNull("FIM_user_name");
        f1 = new ArrayList<>();
        String[] str5 = {"created_user_name"};
        for(String s : str5){
            if(map.containsKey(s)){
                FilterCriteria f = filters.get(map.get(s));
                f.setColName("FIM_user_name");
                f1.add(f);
            }
        }
        CommonUtils.addFilters(f1,queryWrapper5);

        QueryWrapper<Inquiry> queryWrapper6 = new QueryWrapper<>();
        queryWrapper6.isNotNull("customer_name");
        f1 = new ArrayList<>();
        String[] str6 = {"customer_name"};
        for(String s : str6){
            if(map.containsKey(s)){
                f1.add(filters.get(map.get(s)));
            }
        }
        CommonUtils.addFilters(f1,queryWrapper6);

        QueryWrapper<Inquiry> queryWrapper7 = new QueryWrapper<>();
        f1 = new ArrayList<>();
        String[] str7 = {"customer_type","item_type","inquiry_type","delay","order_delivery_progress"};
        for(String s : str7){
            if(map.containsKey(s)){
                f1.add(filters.get(map.get(s)));
            }
        }
        CommonUtils.addFilters(f1,queryWrapper7);
        queryWrapper7.isNotNull("customer_name")
                .isNotNull("created_user_name")
                .isNotNull("salesman_name")
                .and(username!=null,o->o.eq(username!=null,"created_user_name",username)
                        .or().eq(username!=null,"salesman_name",username));


        // 重命名 (原默认为ew)
        queryWrapper1.setParamAlias("qw1");
        queryWrapper2.setParamAlias("qw2");
        queryWrapper3.setParamAlias("qw3");
        queryWrapper4.setParamAlias("qw4");
        queryWrapper5.setParamAlias("qw5");
        queryWrapper6.setParamAlias("qw6");
        queryWrapper7.setParamAlias("qw7");
        return salesOrderVoMapper.selectListByFilter(queryWrapper1,queryWrapper2,
                queryWrapper3,queryWrapper4,queryWrapper5,queryWrapper6,queryWrapper7);
    }

    /**
     * 只是测试
     * @param filters
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> testItemFilter(List<FilterCriteria> filters, int offset, int limit) {

        QueryWrapper<List<Map<String,Object>>> queryWrapper = new QueryWrapper<>();
        CommonUtils.addFilters(filters, queryWrapper);
        return salesOrderVoMapper.getALL(queryWrapper,offset,limit);
    }

    @Override
    public List<Map<String, Object>> getColMaps(Long tableId, Long planId, Long userId) {

        return null;
    }

    @Override
    public String getDocumentNumberFormat(int type) {
        String s = "";
        if(ORDER_TYPE_XD == type){
            s += "XSXD";
        }else if(ORDER_TYPE_YC == type){
            s += "XSYC";
        }
        // 拼接月份
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
        s += date;

        // 获取当月的数量



        return s;
    }

    @Override
    public boolean isValidType(int inquiryType){
        return inquiryType == ORDER_TYPE_YC || inquiryType == ORDER_TYPE_XD ||
                inquiryType == ORDER_TYPE_PO || inquiryType == ORDER_TYPE_PR || inquiryType == ORDER_TYPE_YG;
    }
    @Override
    public Map<String,Object> addValid(Inquiry inquiry) {
        Map<String,Object> map = new HashMap<>();
        if(inquiry.getItemId() == null || null == itemService.findItemById(inquiry.getItemId())) {
            map.put("itemIdMsg","物料不存在！");
        }
        if(inquiry.getSaleNum() == null){
            map.put("saleNumMsg","数量不能为空！");
        }
        if(inquiry.getCustomerId() == null || null == customerService.findCustomerById(inquiry.getCustomerId())){
            map.put("customerMsb","客户不存在！");
        }
        if(inquiry.getInquiryType() == null || !isValidType(inquiry.getInquiryType())){
            map.put("inquiryTypeMsg","订单状态为空或不合法！");
        }
        if(inquiry.getExpectedTime() == null || inquiry.getExpectedTime().before(new Date())){
            map.put("expectedTimeMsg","期待发货日期不存在或早于当前时间");
        }
        if(inquiry.getSalesmanId()!=null&&userService.findUserById(inquiry.getSalesmanId())==null){
            map.put("salesmanMsg","销售员不存在！");
        }
        return map;
    }

    @Override
    public Inquiry getInquiryById(Long orderId) {
        LambdaQueryWrapper<Inquiry> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Inquiry::getState,-1)
                .eq(Inquiry::getInquiryId,orderId);
        return inquiryMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean deleteOrder(Long orderId) {
        LambdaUpdateWrapper<Inquiry> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Inquiry::getInquiryId,orderId)
                .ne(Inquiry::getState,-1)
                .set(Inquiry::getState,-1);
        return inquiryMapper.update(null,updateWrapper)==1;
    }

    @Override
    public boolean isExist(Inquiry inquiry) {
        LambdaQueryWrapper<Inquiry> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inquiry::getInquiryType,inquiry.getInquiryType())
                .eq(Inquiry::getState,inquiry.getState())
                .eq(Inquiry::getArrangedTime,inquiry.getArrangedTime())
                .eq(Inquiry::getCreatedUser,inquiry.getCreatedUser())
                .eq(Inquiry::getExpectedTime,inquiry.getExpectedTime())
                .eq(inquiry.getRemark()!=null,Inquiry::getRemark,inquiry.getRemark())
                .eq(Inquiry::getCustomerId,inquiry.getCustomerId())
                .eq(Inquiry::getSaleNum,inquiry.getSaleNum())
                .eq(Inquiry::getItemId,inquiry.getItemId())
                .eq(Inquiry::getSalesmanId,inquiry.getSalesmanId());
        return inquiryMapper.selectOne(queryWrapper)!=null;
    }

    @Override
    public Integer transferType(String inquiryType) {
        Integer type = -1;
        if(StringUtils.isNotBlank(inquiryType)){
            switch (inquiryType){
                case "YC":
                    type = ORDER_TYPE_YC;
                    break;
                case "PO":
                    type = ORDER_TYPE_PO;
                    break;
                case "PR":
                    type = ORDER_TYPE_PR;
                    break;
                case "YG":
                    type = ORDER_TYPE_YG;
                    break;
                case "XD":
                    type = ORDER_TYPE_XD;
                    break;
                default:
                    type = -1;
            }
        }
        return type;
    }

    @Override
    public Map<String,Object> checkAddByExcel(InquiryModel inquiryModel, int rowIndex) {
        Map<String, Object> map = new HashMap<>();
        Inquiry inquiry = new Inquiry();
        // 添加物料id
        Item item = itemService.findItemByCode(inquiryModel.getItemCode());
        if(item == null){
            map.put("error","第"+rowIndex+"行的物料编码在数据库中不存在，请核对");
            log.error("第"+rowIndex+"行的物料编码在数据库中不存在，请核对");
            return map;
        }
        if(!item.getItemName().equals(inquiryModel.getItemName())){
            map.put("error","第"+rowIndex+"行的物料编码和物料名称在数据库中不是对应的，请核对");
            log.error("第"+rowIndex+"行的物料编码和物料名称在数据库中不是对应的，请核对");
            return map;
        }
        if(inquiryModel.getItemType() == null || item.getItemType()!=itemService.transferItemType(inquiryModel.getItemType())){
            map.put("error","第"+rowIndex+"行的物料编码和物料类型在数据库中不是对应的，请核对");
            log.error("第"+rowIndex+"行的物料编码和物料类型在数据库中不是对应的，请核对");
            return map;
        }
        inquiry.setItemId(item.getId());
        // 添加客户id
        Customer c = customerService.findCustomerByName(inquiryModel.getCustomerName());
        if(c==null){
            map.put("error","第"+rowIndex+"行的客户名称在数据库中不存在，请核对");
            log.error("第"+rowIndex+"行的客户名称在数据库中不存在，请核对");
            return map;
        }
        String ct = customerTypeService.getCustomerTypeByRule(c.getFCustId(),item.getId());
        if(inquiryModel.getCustomerType() == null || !ct.equals(inquiryModel.getCustomerType())){
            map.put("error","第"+rowIndex+"行的客户类型与数据库对应关系不匹配或不存在，请核对");
            log.error("第"+rowIndex+"行的客户名称在数据库中不存在，请核对");
            return map;
        }
        inquiry.setCustomerId(c.getFCustId());
        // 添加销售员id
        User salesman = userService.findSalesmanByName(inquiryModel.getSalesmanName());
        if(salesman == null){
            map.put("error","第"+rowIndex+"行的销售员名称在数据库中不存在，请核对");
            log.error("第"+rowIndex+"行的销售员名称在数据库中不存在，请核对");
            return map;
        }
        inquiry.setSalesmanId(salesman.getId());
        // 设置创建人id
        inquiry.setCreatedUser(hostHolder.getUser().getId());
        // 设置状态为保存
        inquiry.setState(0);
        // 设置订单类型
        int type;
        if((type = transferType(inquiryModel.getInquiryType()))==-1){
            map.put("error","第"+rowIndex+"行的订单类型有误，请修改并重试！");
            log.error("第"+rowIndex+"行的订单类型有误，请修改并重试！");
            return map;
        }
        inquiry.setInquiryType(type);
        // 设置订单编号
        inquiry.setInquiryCode(getDocumentNumberFormat(type));
        // 设置创建时间
        inquiry.setCreatedTime(new Date());
        // 设置相关时间
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            inquiry.setArrangedTime(sdf.parse(inquiryModel.getArrangedTime()));
            inquiry.setExpectedTime(sdf.parse(inquiryModel.getExceptedTime()));
        }catch (Exception e){
            map.put("error","第"+rowIndex+"行的时间数据有误，请检查！");
            log.error("第"+rowIndex+"行的时间数据有误，请检查！");
            return map;
        }
        // 设置备注
        inquiry.setRemark(inquiryModel.getRemark());
        // 设置数量
        try {
            inquiry.setSaleNum(Long.parseLong(inquiryModel.getNum()));
        }catch (Exception e) {
            map.put("error","第"+rowIndex+"行的数量可能不是数字或长度有误，请检查！");
            log.error("第"+rowIndex+"行的数量可能不是数字或长度有误，请检查！");
            return map;
        }
        // 判断是否重复
        if(isExist(inquiry)){
            map.put("error","第"+rowIndex+"行数据在数据库中已存在，请检查是否重复添加！");
            log.error("第"+rowIndex+"行数据在数据库中已存在，请检查是否重复添加！");
            return map;
        }
        map.put("inquiry",inquiry);
        return map;
    }
}
