package org.biot.access.mqtt.driven.msg;

import org.biot.message.BiotMessage;
import org.biot.things.core.dto.msg.request.DeviceConnectedRequest;

/**
 * 上行消息发送
 */
public interface UpstreamMessageSender {
    /**
     * 发送设备上线消息
     *
     * @param message
     */
    void sendConnectedMessage(BiotMessage<DeviceConnectedRequest> message);
}
