package com.benewake.saleordersystem.rabbitmq;

import com.benewake.saleordersystem.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Lcs
 * @since 2023年07月24 11:04
 * 描 述： TODO
 */
@Component
public class ReceiveHandler {
    //监听email队列
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(Object msg, Message message, Channel channel){
        System.out.println("QUEUE_INFORM_EMAIL msg"+msg);
    }
    //监听sms队列
    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
    public void receive_sms(Object msg, Message message, Channel channel){
        System.out.println("QUEUE_INFORM_SMS msg"+msg);
    }
}
