package org.example.service.impl;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.example.domin.Goods;
import org.example.service.ReceiveService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Slf4j
@Service
public class ReceiveServiceImpl implements ReceiveService {

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public void receive() {
        String message = (String)amqpTemplate.receiveAndConvert("directQueue");
        System.out.println("消息------>" + message);
    }

    /**
     * 监听消息
     * @param message
     */
    @RabbitListener(queues = {"directQueue"})
    public void directReceive(String message) {
        System.out.println("监听到的消息------>" + message);
    }

    /**
     * fanout监听消息01
     * @param message
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(), exchange = @Exchange(name = "fanoutExchange", type = "fanout"))})
    public void fanoutReceive01(String message) {
        System.out.println("fanout01监听到的消息------>" + message);
    }

    /**
     * fanout监听消息02
     * @param message
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue(), exchange = @Exchange(name = "fanoutExchange", type = "fanout"))})
    public void fanoutReceive02(String message) {
        System.out.println("fanout02监听到的消息------>" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue("topic01"), key = {"aa"}, exchange = @Exchange(name = "topicExchange", type = "topic"))})
    public void topicReceive01(String message) {
        System.out.println("topicReceive01监听到的消息aa------->" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue("topic02"), key = {"aa.*"}, exchange = @Exchange(name = "topicExchange", type = "topic"))})
    public void topicReceive02(String message) {
        System.out.println("topicReceive02监听到的消息aa.*------->" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(value = @Queue("topic03"), key = {"aa.#"}, exchange = @Exchange(name = "topicExchange", type = "topic"))})
    public void topicReceive03(String message) {
        System.out.println("topicReceive03监听到的消息aa.#------->" + message);
    }

    /**
     * 监听消息
     * @param goods
     */
    @RabbitListener(queues = {"normalQueue"})
    public void normalReceive(Goods goods, Message message, Channel channel) {
        log.info("接收时间:{}", LocalDateTime.now());
        System.out.println("normalReceive监听到的消息------>" + goods);
    }

    /**
     * 监听消息
     * @param message
     */
    @RabbitListener(queues = {"deadQueue"})
    public void deadReceive(Goods goods, Message message, Channel channel) {
        log.info("接收时间:{}", LocalDateTime.now());
        System.out.println("deadReceive监听到的消息------>" + goods);
    }

    /**
     * 监听消息
     * @param message
     */
    @RabbitListener(queues = {"delayedQueue"})
    public void delayedQueueReceive(Goods goods, Message message, Channel channel) {
        log.info("接收时间:{}", LocalDateTime.now());
        System.out.println("delayedQueue监听到的消息------>" + goods);
    }
}
