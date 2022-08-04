package org.biot.access.mqtt.mq;

import org.biot.access.mqtt.driven.msg.UpstreamMessageSender;
import org.biot.message.BiotMessage;
import org.biot.things.core.dto.msg.request.DeviceConnectedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpstreamMessageSenderDelegate implements UpstreamMessageSender {
    @Autowired
    private SendClientFactory clientFactory;

    /**
     * 发送设备上线消息
     *
     * @param message
     */
    @Override
    public void sendConnectedMessage(BiotMessage<DeviceConnectedRequest> message) {
        clientFactory.get().sendConnectedMessage(message);
    }
}
