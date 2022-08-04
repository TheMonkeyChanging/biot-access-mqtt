package org.biot.access.mqtt.broker.emqx.msg;

import org.biot.access.mqtt.service.DeviceUpstreamMessageService;
import org.biot.access.mqtt.util.DeviceUtil;
import org.biot.things.core.dto.msg.request.BaseDeviceRequest;
import org.biot.things.core.dto.msg.request.DeviceConnectedRequest;
import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 设备上线监听
 */
@Component
public class ConnectMessageListener implements IMqttMessageListener {
    private static Logger log = LoggerFactory.getLogger(ConnectMessageListener.class);

    @Autowired
    private DeviceUpstreamMessageService upstreamMessageService;

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Receive {} msg with payload: {}.", topic, message);
        }

        ConnectedPayload payload = new ConnectedPayload(message.getPayload());
        DeviceConnectedRequest request = buildRequest(payload);
        upstreamMessageService.sendConnectedMessage(request);
    }

    /**
     * 构建连接请求参数
     *
     * @param payload
     * @return
     */
    private DeviceConnectedRequest buildRequest(ConnectedPayload payload) {
        DeviceConnectedRequest request = new DeviceConnectedRequest();
        // wideUserName格式为：deviceId#productId#timestamp
        String[] strArr = DeviceUtil.parserWideUserName(payload.getWideUserName());
        request.setDeviceId(strArr[0]);
        request.setProductId(strArr[1]);
        request.setClientId(payload.getClientId());
        request.setConnectTime(payload.getConnectTime());

        return request;
    }
}
