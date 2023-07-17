package com.benewake.saleordersystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.benewake.saleordersystem.entity.Delivery;
import com.benewake.saleordersystem.entity.Inquiry;
import com.benewake.saleordersystem.entity.sfexpress.Route;
import com.benewake.saleordersystem.mapper.DeliveryMapper;
import com.benewake.saleordersystem.entity.Past.SaleOut;
import com.benewake.saleordersystem.service.DeliveryService;
import com.benewake.saleordersystem.service.KingDeeService;
import com.benewake.saleordersystem.service.SFExpressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lcs
 * @since 2023年07月15 09:37
 * 描 述： TODO
 */
@Service
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryMapper deliveryMapper;
    @Autowired
    private KingDeeService kingDeeService;
    @Autowired
    private SFExpressService sFExpressService;

    /**
     * 获取未签收的运输单号列表
     * @return
     */
    public List<Delivery> findUnfinished() {
        LambdaQueryWrapper<Delivery> lqw = new LambdaQueryWrapper<>();
        lqw.select(Delivery::getDeliveryCode,Delivery::getInquiryCode,Delivery::getDeliveryPhone)
                .isNull(Delivery::getReceiveTime)
                .isNotNull(Delivery::getDeliveryCode);
        return deliveryMapper.selectList(lqw);
    }

    /**
     * 更新信息订单号和手机号
     * @param delivery
     * @return
     */
    public int updateDelivery(Delivery delivery) {
        LambdaUpdateWrapper<Delivery> luw = new LambdaUpdateWrapper<>();
        luw.eq(Delivery::getInquiryCode,delivery.getInquiryCode())
                .set(Delivery::getDeliveryCode,delivery.getDeliveryCode())
                .set(Delivery::getDeliveryPhone,delivery.getDeliveryPhone());
        return deliveryMapper.update(null,luw);
    }

    @Override
    public boolean updateDelivery() {
        try{
            // 尝试获取没有运输单号或手机号的订单号
            LambdaQueryWrapper<Delivery> lqw = new LambdaQueryWrapper<>();
            lqw.select(Delivery::getInquiryCode)
                    .and(a->a.isNull(Delivery::getDeliveryCode).or().eq(Delivery::getDeliveryCode,"").or()
                            .isNull(Delivery::getDeliveryPhone).or().eq(Delivery::getDeliveryPhone,""));
            List<Delivery> lists = deliveryMapper.selectList(lqw);
            // 获取订单对应的运输单号和手机号
            List<SaleOut> saleOuts = kingDeeService.selectFCarriageNO(lists);
            System.out.println(saleOuts.size());
            // 在数据库中更新运输单号和手机号
            saleOuts.forEach(s->{
                System.out.println(s.toString());
                Delivery delivery = new Delivery();
                delivery.setInquiryCode(s.getFNOTE());
                delivery.setDeliveryCode(s.getFCarriageNO());
                delivery.setDeliveryPhone(StringUtils.isBlank(s.getF_ora_Text2())?null:s.getF_ora_Text2()
                        .substring(s.getF_ora_Text2().length()-4));
                updateDelivery(delivery);
            });

            // 获取所有状态未签收的订单信息
            List<Delivery> deliveries = findUnfinished();
            System.out.println(deliveries.size());
            // 获取最新运输状态 并更新
            deliveries.forEach(c->{
                try {
                    Route r = sFExpressService.getLastestRouteByFCarriageNO(c);
                    //System.out.println(r.toString());
                    if(r!=null){
                        if("80".equals(r.getOpCode())){
                            c.setReceiveTime(r.getAcceptTime());
                        }
                        c.setDeliveryState(r.getOpCode()==null?null:Integer.parseInt(r.getOpCode()));
                        c.setDeliveryLastestState(r.getRemark());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            // 存入数据库
            deliveryMapper.updateDeliveries(deliveries);
            log.info("运输信息更新完成！");
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public int insertLists(List<Inquiry> lists) {
        return deliveryMapper.insertLists(lists);
    }
}
