package org.biot.access.mqtt.mq;

import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class SendClientFactory {
    @Setter
    private static SendClient sendClient;

    public SendClient get() {
        return sendClient;
    }
}
