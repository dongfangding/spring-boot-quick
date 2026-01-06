package com.ddf.boot.quickstart.core.controller.features;

import com.ddf.boot.common.api.model.common.response.ResponseData;
import com.ddf.common.boot.mqtt.client.MqttPublishClient;
import com.ddf.common.boot.mqtt.model.request.InnerMqttMessageRequest;
import com.ddf.common.boot.mqtt.model.response.MqttMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>mqtt 发送控制器</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/03/19 23:19
 */
@RestController
@RequestMapping("mqtt")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class MqttController {

    @Autowired(required = false)
    private MqttPublishClient mqttPublishClient;

    @PostMapping("im/c2c/publish")
    public ResponseData<MqttMessageResponse> publishMessage(@RequestBody InnerMqttMessageRequest request) {
        return mqttPublishClient.publish(request);
    }
}
