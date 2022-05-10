package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.domin.Goods;
import org.example.service.SendService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class SendServiceImpl implements SendService {

    @Resource
    private AmqpTemplate amqpTemplate;


    @Override
    public void sendMessage() {
        /**
         * 参数1：交换机名
         * 参数2：routingKey
         * 参数3：消息
         */
        amqpTemplate.convertAndSend("directExchange", "directRoutingKey", "一个消息");
    }

    @Override
    public void sendFanoutMessage() {
        amqpTemplate.convertAndSend("fanoutExchange", "", "fanout消息");
    }

    @Override
    public void sendTopicMessage() {
        amqpTemplate.convertAndSend("topicExchange", "aa", "topic消息");
    }

    @Override
    public void sendNormalMessage() {

        log.info("===============队列生产消息====================");
        log.info("发送时间:{}", LocalDateTime.now());
//        for (int i = 1; i <= 10; i++) {
//            amqpTemplate.convertAndSend(
//                    "normalExchange",
//                    "normalRoutingKey",
//                    ("消息" + i).getBytes(StandardCharsets.UTF_8),
//                    message -> {
//                        //注意这里时间要是字符串形式
//                        message.getMessageProperties().setExpiration("10000");
//                        return message;
//                    });
//        }
        Goods goods = new Goods();
        goods.setName("华为平板").setId(1001L).setNum(100);
        amqpTemplate.convertAndSend(
                "normalExchange",
                "normalRoutingKey",
                goods,
                message -> {
                    //注意这里时间要是字符串形式
                    message.getMessageProperties().setExpiration("10000");
                    return message;
                });
        log.info("{}ms后过期", 10000);
    }

    @Override
    public void sendDelayedMessage(Integer time) {
        log.info("===============队列生产消息====================");
        log.info("发送时间:{}", LocalDateTime.now());
        Goods goods = new Goods();
        goods.setName("华为平板").setId(1001L).setNum(100);
        amqpTemplate.convertAndSend(
                "delayedExchange",
                "delayedRoutingKey",
                goods,
                message -> {
                    //注意这里时间要是字符串形式
                    message.getMessageProperties().setDelay(time);
                    return message;
                });
        log.info("{}ms后发送", time);
    }
}
