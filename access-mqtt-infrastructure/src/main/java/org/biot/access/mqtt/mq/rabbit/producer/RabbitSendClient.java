package org.biot.access.mqtt.mq.rabbit.producer;

import org.biot.access.mqtt.mq.SendClient;
import org.biot.access.mqtt.mq.SendClientFactory;
import org.biot.message.BiotMessage;
import org.biot.things.core.dto.msg.request.DeviceConnectedRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.biot.access.mqtt.mq.rabbit.RabbitConfig.ACCESS_EXCHANGE;
import static org.biot.access.mqtt.mq.rabbit.RabbitConfig.ROUTE_KEY_CONNECT;


@ConditionalOnExpression("'rabbit'.equals('${biot.access.mq.type}')")
@Component
public class RabbitSendClient implements SendClient {
    private static final Logger log = LoggerFactory.getLogger(RabbitSendClient.class);

    @Autowired
    private RabbitProducer rabbitProducer;

    /**
     * 发送设备上线消息
     *
     * @param connectedMessage
     */
    @Override
    public void sendConnectedMessage(BiotMessage<DeviceConnectedRequest> connectedMessage) {
        rabbitProducer.send(ACCESS_EXCHANGE, ROUTE_KEY_CONNECT, connectedMessage);
    }

    /**
     * 发送设备下线消息
     *
     * @param connectedMessage
     */
    @Override
    public void sendDisconnectedMessage(BiotMessage connectedMessage) {
        rabbitProducer.send(ACCESS_EXCHANGE, ROUTE_KEY_CONNECT, connectedMessage);
    }

    @PostConstruct
    private void registerToFactory() {
        log.warn("Set send client with rabbit-client!");
        SendClientFactory.setSendClient(this);
    }

}
