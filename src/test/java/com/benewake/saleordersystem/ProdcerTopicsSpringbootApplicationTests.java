package com.benewake.saleordersystem;

import com.benewake.saleordersystem.config.RabbitmqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Lcs
 * @since 2023年07月24 11:03
 * 描 述： TODO
 */
@SpringBootTest
@ContextConfiguration(classes = SaleOrderSystemApplication.class)
public class ProdcerTopicsSpringbootApplicationTests {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void Producer_topics_springbootTest() {
        /**
         * 参数：
         * 1、交换机名称
         * 2、routingKey
         * 3、消息内容
         */
        String messageId = UUID.randomUUID().toString();
        String messageData = "test message,hello!";
        String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Map<String,Object> map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("data",messageData);
        map.put("current",current);
        rabbitTemplate.convertAndSend("NoticeDirectExchange", "notice", map, new CorrelationData(UUID.randomUUID().toString()));

    }

}
