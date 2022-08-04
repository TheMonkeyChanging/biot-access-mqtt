package org.biot.access.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"org.biot.things.core.client"})
public class AccessMqttApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccessMqttApplication.class, args);
    }
}
