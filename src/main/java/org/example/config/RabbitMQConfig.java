package org.example.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitMQConfig {

    private final String NORMAL_EXCHANGE = "normalExchange";
    private final String NORMAL_QUEUE = "normalQueue";
    private final String DEAD_EXCHANGE = "deadExchange";
    private final String DEAD_QUEUE = "deadQueue";
    private final String DELAYED_QUEUE = "delayedQueue";
    private final String DELAYED_EXCHANGE = "delayedExchange";

    //配置direct类型的交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    //配置队列
    @Bean
    public Queue directQueue() {
        return new Queue("directQueue");
    }

    //配置一个队列和交换机绑定
    @Bean
    public Binding directBinding(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("directRoutingKey");
    }

    //配置fanout类型的交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    //配置topic类型的交换机
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    //配置direct类型的交换机
    @Bean
    public DirectExchange normalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    //配置队列
    @Bean
    public Queue normalQueue() {
        HashMap<String, Object> arguments = new HashMap<>();
        //正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key", "deadRoutingKey");
        //设置过期时间
//        arguments.put("x-message-ttl", 10000);
        //设置正常队列的长度限制
//        arguments.put("x-max-length", 6);
        return new Queue(NORMAL_QUEUE, true, false, false, arguments);
    }

    //普通队列和普通交换机绑定
    @Bean
    public Binding normalBinding(Queue normalQueue, DirectExchange normalExchange) {
        return BindingBuilder.bind(normalQueue).to(normalExchange).with("normalRoutingKey");
    }

    //配置direct类型的死信交换机
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE);
    }

    //配置死信队列
    @Bean
    public Queue deadQueue() {
        return new Queue(DEAD_QUEUE);
    }


    //死信队列和死信交换机绑定
    @Bean
    public Binding deadBinding(Queue deadQueue, DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with("deadRoutingKey");
    }

    //配置direct类型的延迟交换机
    @Bean
    public CustomExchange delayedExchange() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, arguments);
    }

    //配置延迟队列
    @Bean
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE);
    }

    //延迟队列和延迟交换机绑定
    @Bean
    public Binding delayedBinding(Queue delayedQueue, CustomExchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with("delayedRoutingKey").noargs();
    }
}
