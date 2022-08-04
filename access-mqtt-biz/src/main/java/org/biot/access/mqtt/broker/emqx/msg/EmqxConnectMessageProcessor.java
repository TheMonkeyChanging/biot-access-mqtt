package org.biot.access.mqtt.broker.emqx.msg;

import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EmqxConnectMessageProcessor {
    private static Logger log = LoggerFactory.getLogger(EmqxConnectMessageProcessor.class);

    private static final String CONNECTED_TOPIC = "clients/connected";
    private static final String DISCONNECTED_TOPIC = "clients/disconnected";
//    public static final String CONNECTED_TOPIC = "$SYS/brokers/${node}/clients/${clientid}/connected";
//    public static final String DISCONNECTED_TOPIC = "$SYS/brokers/${node}/clients/${clientid}/disconnected";

    @Value("${biot.mqtt.emqx.host}")
    private String host;

    @Autowired
    private ConnectMessageListener connectMessageListener;

    @Autowired
    private DisconnectMessageListener disconnectMessageListener;

    private MqttClient client;

    @PostConstruct
    private void connect() throws MqttException {
        String clientId = "access-mqtt-" + System.currentTimeMillis();
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setCleanStart(false);
        options.setAutomaticReconnect(true);
        client = new MqttClient(host, clientId, persistence);
        client.connect(options);

        subscribe(CONNECTED_TOPIC, connectMessageListener);
        subscribe(DISCONNECTED_TOPIC, disconnectMessageListener);

        log.info("Mqtt-client is init!");
    }

    /**
     * 订阅消息
     *
     * @param topic
     * @param listener
     * @throws MqttException
     */
    private void subscribe(String topic, IMqttMessageListener listener) throws MqttException {
        MqttSubscription[] subscriptions = new MqttSubscription[]{new MqttSubscription(topic)};
        IMqttMessageListener[] subscriptionListeners = new IMqttMessageListener[]{listener};
        client.subscribe(subscriptions, subscriptionListeners);
    }

}
