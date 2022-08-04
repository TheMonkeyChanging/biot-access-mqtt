package org.biot.access.mqtt.service;

import org.biot.BiotResult;
import org.biot.things.core.client.device.DeviceClient;
import org.biot.things.core.dto.device.DeviceAuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DeviceConnectAuthService {
    private static final String DELIMITER = "#";

    @Autowired
    private DeviceClient deviceClient;

    /**
     * 接入认证
     *
     * @param clientId
     * @param wideUserName
     * @param encryptPassword
     * @return
     */
    public boolean validateAuth(@NonNull String clientId, @NonNull String wideUserName,
                                @NonNull String encryptPassword) {
        DeviceAuthRequest authRequest = buildAuthRequest(clientId, wideUserName, encryptPassword);
        BiotResult<Boolean> rs = deviceClient.doAuth(authRequest);
        return rs.getResult();
    }

    /**
     * 构建认证请求参数，wideUserName格式为：deviceId#productId#timestamp
     *
     * @param clientId
     * @param wideUserName
     * @param encryptPassword
     * @return
     */
    private DeviceAuthRequest buildAuthRequest(String clientId, String wideUserName, String encryptPassword) {
        String[] strArr = wideUserName.split(DELIMITER);
        DeviceAuthRequest request = new DeviceAuthRequest();
        request.setDeviceId(strArr[0]);
        request.setProductId(strArr[1]);
        request.setPassword(encryptPassword);
        request.setContent(clientId + DELIMITER + wideUserName);
        return request;
    }
}
