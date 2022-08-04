package org.biot.access.mqtt.broker.emqx.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.biot.access.mqtt.service.DeviceConnectAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * EMQX的HTTP认证和授权
 */
@RestController
@RequestMapping("/emqx")
public class EmqxAuthController {
    @Autowired
    private DeviceConnectAuthService deviceConnectAuthService;

    /**
     * 使用EMQX作为MQTT Broker时，Http认证器
     *
     * @param clientId
     * @param param
     * @return
     */
    @PostMapping("/authentication/{clientId}")
    public String authentication(@PathVariable("clientId") String clientId, @RequestBody AuthParam param) {
        boolean passed = deviceConnectAuthService.validateAuth(clientId, param.getUsername(), param.getPassword());
        return passed ? Result.ALLOW_RESULT.toString() : Result.DENY.toString();
    }

    /**
     * 使用EMQX作为MQTT Broker时，Http授权器
     *
     * @param clientId
     * @param param
     * @return
     */
    @PostMapping("/authorization")
    public String authorization(@PathVariable("clientId") String clientId, @RequestBody AuthorizationParam param) {

        return Result.ALLOW_RESULT.toString();
    }

    /**
     * 认证器请求参数
     */
    private static class AuthParam {
        @Getter @Setter
        private String username;

        @Getter @Setter
        private String password;
    }

    /**
     * 授权器请求参数
     */
    private static class AuthorizationParam {
        @Getter @Setter
        private String username;

        @Getter @Setter
        private String topic;

        @Getter @Setter
        private String action;
    }

    /**
     * 认证和授权应答结果
     */
    private static class Result {
        private static final String ALLOW = "allow";
        static final Result ALLOW_RESULT = new Result(ALLOW);
        private static final String DENY = "deny";
        static final Result DENY_RESULT = new Result(DENY);
        private static final String IGNORE = "ignore";
        static final Result IGNORE_RESULT = new Result(IGNORE);

        private final String result;
        private final Boolean superUser;

        private Result(@NonNull String result) {
            this.result = result;
            this.superUser = false;
        }

        public String toString() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", result);
            jsonObject.put("is_superuser", superUser);
            return jsonObject.toString();
        }
    }
}
