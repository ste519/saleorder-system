package com.benewake.saleordersystem;

import com.benewake.saleordersystem.config.RabbitmqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

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

        //使用rabbitTemplate发送消息
        String message = "send email message to user";
        /**
         * 参数：
         * 1、交换机名称
         * 2、routingKey
         * 3、消息内容
         */
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.email", message);

    }

}
