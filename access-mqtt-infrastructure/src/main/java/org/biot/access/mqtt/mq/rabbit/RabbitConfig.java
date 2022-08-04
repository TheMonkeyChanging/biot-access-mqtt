package org.biot.access.mqtt.mq.rabbit;

import org.biot.access.mqtt.mq.rabbit.producer.RabbitProducer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnExpression("'rabbit'.equals('${biot.access.mq.type}')")
@Configuration
public class RabbitConfig {
    public static final String ACCESS_EXCHANGE = "access";
    public static final String ACCESS_CONNECT_QUEUE = "access_connect_queue";
    public static final String ROUTE_KEY_CONNECT = "connect";
    public static final String ACCESS_PROPERTY_QUEUE = "access_property_queue";
    public static final String ROUTE_KEY_PROPERTY = "property";
    public static final String ACCESS_EVENT_QUEUE = "access_event_queue";
    public static final String ROUTE_KEY_EVENT = "event";
    public static final String ACCESS_FUNCTION_QUEUE = "access_function_queue";
    public static final String ROUTE_KEY_FUNCTION = "function";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    RabbitProducer rabbitProducer() {
        return new RabbitProducer(rabbitTemplate);
    }

    @Bean
    DirectExchange accessExchange() {
        return new DirectExchange(ACCESS_EXCHANGE, true, false);
    }

    /**
     * 连接相关消息队列，包括上下线消息、心跳消息
     *
     * @return
     */
    @Bean
    Queue accessConnectQueue() {
        return new Queue(ACCESS_CONNECT_QUEUE, true, false, false);
    }

    /**
     * 属性上报消息队列，用于将设备属性上报至平台
     *
     * @return
     */
    @Bean
    Queue accessPropertyQueue() {
        return new Queue(ACCESS_PROPERTY_QUEUE, true, false, false);
    }

    /**
     * 事件上报消息队列，用于将设备事件上报至平台
     *
     * @return
     */
    @Bean
    Queue accessEventQueue() {
        return new Queue(ACCESS_EVENT_QUEUE, true, false, false);
    }

    /**
     * 设备函数调用消息队列，用于接收来自平台的下行指令
     *
     * @return
     */
    @Bean
    Queue accessFunctionQueue() {
        return new Queue(ACCESS_FUNCTION_QUEUE, true, false, false);
    }

    @Bean
    Binding connectBinding() {
        return BindingBuilder.bind(accessConnectQueue()).to(accessExchange()).with(ROUTE_KEY_CONNECT);
    }

    @Bean
    Binding propertyBinding() {
        return BindingBuilder.bind(accessPropertyQueue()).to(accessExchange()).with(ROUTE_KEY_PROPERTY);
    }

    @Bean
    Binding eventBinding() {
        return BindingBuilder.bind(accessEventQueue()).to(accessExchange()).with(ROUTE_KEY_EVENT);
    }

    @Bean
    Binding functionBinding() {
        return BindingBuilder.bind(accessFunctionQueue()).to(accessExchange()).with(ROUTE_KEY_FUNCTION);
    }
}
