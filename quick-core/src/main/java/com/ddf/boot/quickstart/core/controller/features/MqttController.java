package com.ddf.boot.quickstart.core.controller.features;

import com.ddf.boot.common.api.model.common.response.response.ResponseData;
import com.ddf.common.boot.mqtt.client.MqttPublishClient;
import com.ddf.common.boot.mqtt.model.request.MqttMessageRequest;
import com.ddf.common.boot.mqtt.model.support.body.TextMessageBody;
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

    private final MqttPublishClient mqttPublishClient;

    @PostMapping("im/c2c/publish")
    public ResponseData<Void> publishMessage(@RequestBody MqttMessageRequest<TextMessageBody> request) {
        mqttPublishClient.publish(request);
        return ResponseData.empty();
    }
}
