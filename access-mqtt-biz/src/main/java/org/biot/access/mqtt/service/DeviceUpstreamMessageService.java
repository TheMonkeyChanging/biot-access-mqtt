package org.biot.access.mqtt.service;

import org.biot.access.mqtt.driven.msg.UpstreamMessageSender;
import org.biot.message.BiotMessage;
import org.biot.message.BiotMessageType;
import org.biot.things.core.dto.msg.request.DeviceConnectedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备上行消息服务
 */
@Service
public class DeviceUpstreamMessageService {
    @Autowired
    private UpstreamMessageSender messageSender;

    /**
     * 发送设备上线消息
     *
     * @param request
     */
    public void sendConnectedMessage(DeviceConnectedRequest request) {
        BiotMessage<DeviceConnectedRequest> message = BiotMessage.of(BiotMessageType.DEVICE_ONLINE, request);
        messageSender.sendConnectedMessage(message);
    }
}
