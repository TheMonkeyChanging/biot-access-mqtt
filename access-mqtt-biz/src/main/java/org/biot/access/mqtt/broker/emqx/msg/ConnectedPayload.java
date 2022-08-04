package org.biot.access.mqtt.broker.emqx.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

@Getter
public class ConnectedPayload {
    private String clientId;
    private String wideUserName;
    private long connectTime;

    ConnectedPayload(byte[] payload) {
        parse(payload);
    }

    /**
     * payload 格式为：
     {
     "username": "foo",
     "ts": 1625572213873,
     "sockport": 1883,
     "proto_ver": 4,
     "proto_name": "MQTT",
     "keepalive": 60,
     "ipaddress": "127.0.0.1",
     "expiry_interval": 0,
     "connected_at": 1625572213873,
     "connack": 0,
     "clientid": "emqtt-8348fe27a87976ad4db3",
     "clean_start": true
     }
     *
     * @param payload
     */
    private void parse(byte[] payload) {
        String content = new String(payload);
        JSONObject jsonObject = JSON.parseObject(content);
        wideUserName = jsonObject.getString("username");
        connectTime = jsonObject.getLong("connected_at");
        clientId = jsonObject.getString("clientid");
    }
}
