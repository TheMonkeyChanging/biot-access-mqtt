package org.biot.access.mqtt.broker.emqx.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class DisconnectedPayload {
    /*
    {
    "username": "foo",
    "ts": 1625572213873,
    "sockport": 1883,
    "reason": "tcp_closed",
    "proto_ver": 4,
    "proto_name": "MQTT",
    "ipaddress": "127.0.0.1",
    "disconnected_at": 1625572213873,
    "clientid": "emqtt-8348fe27a87976ad4db3"
     */

    private String clientId;
    private String wideUserName;
    private String reason;
    private long disconnectTime;

    DisconnectedPayload(byte[] payload) {
        parse(payload);
    }

    /**
     *
     *
     * @param payload
     */
    private void parse(byte[] payload) {
        String content = new String(payload);
        JSONObject jsonObject = JSON.parseObject(content);
        clientId = jsonObject.getString("clientid");
        wideUserName = jsonObject.getString("username");
        reason  = jsonObject.getString("reason");
        disconnectTime = jsonObject.getByte("connected_at");
    }
}
