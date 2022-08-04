package org.biot.access.mqtt.util;

import lombok.NonNull;

public class DeviceUtil {
    private static final String DELIMITER = "#";

    /**
     * wideUserName格式为：deviceId#productId#timestamp
     *
     * @param wideUserName
     * @return
     */
    public static String[] parserWideUserName(@NonNull String wideUserName) {
        return wideUserName.split(DELIMITER);
    }
}
