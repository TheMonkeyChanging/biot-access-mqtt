package org.biot.access.mqtt.mq;

import org.biot.message.BiotMessage;
import org.biot.things.core.dto.msg.request.DeviceConnectedRequest;

public interface SendClient {
    /**
     * 发送设备上线消息
     *
     * @param connectedMessage
     */
    void sendConnectedMessage(BiotMessage<DeviceConnectedRequest> connectedMessage);

    /**
     * 发送设备下线消息
     *
     * @param connectedMessage
     */
    void sendDisconnectedMessage(BiotMessage connectedMessage);
}
