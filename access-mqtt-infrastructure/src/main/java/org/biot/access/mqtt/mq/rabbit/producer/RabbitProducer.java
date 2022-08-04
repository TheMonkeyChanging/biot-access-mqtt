package org.biot.access.mqtt.mq.rabbit.producer;

import org.biot.message.BiotMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

public class RabbitProducer {
    private RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送消息
     *
     * @param exchange
     * @param routingKey
     * @param message
     */
    public void send(String exchange, String routingKey, BiotMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message, new CorrelationData(UUID.randomUUID().toString()));
    }
}
