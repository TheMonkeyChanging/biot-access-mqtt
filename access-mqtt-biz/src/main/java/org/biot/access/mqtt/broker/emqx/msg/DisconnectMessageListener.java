package org.biot.access.mqtt.broker.emqx.msg;

import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 设备下线监听
 */
@Component
public class DisconnectMessageListener implements IMqttMessageListener {
    private static Logger log = LoggerFactory.getLogger(DisconnectMessageListener.class);

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Receive {} msg[{}] with payload: {}.", topic, message.getId(), message);
        }

        System.out.println(topic);
        System.out.println(message);
    }
}
