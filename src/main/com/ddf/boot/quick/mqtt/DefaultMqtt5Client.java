package com.ddf.boot.quick.mqtt;

import cn.hutool.core.util.IdUtil;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientBuilder;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PayloadFormatIndicator;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * $
 * <p>
 * <p>
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ___/`---'\____
 * .   ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * .............................................
 * 佛曰：bug泛滥，我已瘫痪！
 *
 * @author dongfang.ding
 * @date 2019/12/24 0024 18:42
 */
@Slf4j
public class DefaultMqtt5Client {

    private Mqtt5AsyncClient mqtt5AsyncClient;

    private boolean isConnAck = false;

    public DefaultMqtt5Client() {
        init();
    }

    public void init() {
        MqttClientBuilder clientBuilder = MqttClient.builder()
                .identifier(IdUtil.objectId())
                .serverHost("localhost").serverPort(1883)
//                .addConnectedListener(null)
//                .addDisconnectedListener(null)
//                .executorConfig(null)
                .automaticReconnectWithDefaultConfig();
        mqtt5AsyncClient = clientBuilder.useMqttVersion5().buildAsync();
        mqtt5AsyncClient.connectWith().sessionExpiryInterval(60).keepAlive(120)
//                .simpleAuth().username("").password("".getBytes()).applySimpleAuth()

                // 当客户端不正确断开或者原因代码为DISCONNECT_WITH_WILL_MESSAGE时，会由代理发布一条消息
                .willPublish().topic("/will").qos(MqttQos.AT_LEAST_ONCE).payload("发布遗嘱".getBytes())
                .retain(true).messageExpiryInterval(100).delayInterval(10).payloadFormatIndicator(Mqtt5PayloadFormatIndicator.UTF_8)
                .contentType("text/plain").responseTopic("response/topic").correlationData("correlationData".getBytes())
                .userProperties()
                .add("key1", "value1")
                .add("key2", "value2")
                .applyUserProperties()
                .applyWillPublish()

                // 限制
                .restrictions().receiveMaximum(16)
                .sendMaximum(32)
                .maximumPacketSize(2048)
                .sendMaximumPacketSize(1024)
                .topicAliasMaximum(16)
                .sendTopicAliasMaximum(8)
                .requestProblemInformation(false)
                .requestResponseInformation(true)
                .applyRestrictions()

                .send().whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        log.info("连接建立失败: {}", throwable);
                    } else {
                        subscribe("/demo");
                        isConnAck = true;
                        log.info("连接建立成功: {}", connAck);
                    }
                });
    }

    public void publish(String topic, String message) {
        long before = System.currentTimeMillis();
        while (!isConnAck) {
            if (System.currentTimeMillis() - before > 5000) {
                break;
            }
        }
        mqtt5AsyncClient.publishWith().topic(topic).payload(message.getBytes())
                .qos(MqttQos.EXACTLY_ONCE).send().whenComplete((publish, throwable) -> {
            if (throwable != null) {
                log.error("发送失败");
            } else {
                log.info("消息发送成功>>>>>>: {}", message);
            }
        });
    }

    public void subscribe(String topic) {
        mqtt5AsyncClient.subscribeWith().topicFilter(topic).callback(publish -> {
            log.info("成功订阅到消息>>>>>>>>>>: {}", new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8));
        }).send()
        .whenComplete((subAck, throwable) -> {
            if (throwable != null) {
                // Handle failure to subscribe
            } else {
                // handle successful publish, e.g. logging or incrementing a metric
            }
        });
    }
}
