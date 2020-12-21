package com.ddf.boot.quick.mqtt;

import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperty;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

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
 * @date 2019/12/25 0025 16:00
 */
@Slf4j
public class MqttPublishSubscribeEventImpl implements MqttPublishSubscribeEvent {

    /**
     * 发布主题完成事件
     * 只是调用完成，成功还是失败，需要自己判断，没有区分出来两个事件
     *
     * @param defaultMqtt5Client 当前客户端
     * @param mqtt5PublishResult 发布结果
     * @return void
     * @author dongfang.ding
     * @date 2019/12/26 0026 10:44
     **/
    @Override
    public void onPublishComplete(DefaultMqtt5Client defaultMqtt5Client, Mqtt5PublishResult mqtt5PublishResult) {
        if (mqtt5PublishResult.getError()
                .isPresent()) {
            log.error("[{}]发布消息失败！", mqtt5PublishResult.getPublish()
                    .getTopic(), mqtt5PublishResult.getError()
                    .get());
        } else {
            MqttTopic topic = mqtt5PublishResult.getPublish()
                    .getTopic();
            if (topic.filter()
                    .matches(MqttTopic.of("/chatRoom"))) {
                //
            }
            log.info("[{}]发布消息成功!", new String(mqtt5PublishResult.getPublish()
                    .getPayloadAsBytes(), StandardCharsets.UTF_8));
        }
    }

    /**
     * 在创建完所有的订阅之后，会预留一个接收到数据之后的异步回调接口，在那里把数据传输到该接口中，使用方可以接收到数据之后
     * 根据不同的类型和topic去处理数据
     *
     * @param defaultMqtt5Client 当前客户端
     * @param mqtt5Publish       发布结果
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 15:58
     **/
    @Override
    public void onSubscribeCallback(DefaultMqtt5Client defaultMqtt5Client, Mqtt5Publish mqtt5Publish) {
        MqttTopic topic = mqtt5Publish.getTopic();
        // 在这里可以根据不同的topic去分发不同的类去处理业务了
        log.info("接收到[{}]的数据: {}", topic, new String(mqtt5Publish.getPayloadAsBytes()));
    }

    /**
     * 订阅完成，即执行完订阅操作，成功还是失败，需要自己根据返回值判断；
     * 注意不要与{@link MqttPublishSubscribeEventImpl#onSubscribeCallback} 混淆
     *
     * @param defaultMqtt5Client 当前客户端
     * @param mqttSubscribe      订阅主题集合
     * @param mqtt5SubAck        订阅结果
     * @param throwable          异常
     * @return void
     * @author dongfang.ding
     * @date 2019/12/26 0026 10:43
     **/
    @Override
    public void onSubscribeComplete(DefaultMqtt5Client defaultMqtt5Client, MqttSubscribe mqttSubscribe,
            Mqtt5SubAck mqtt5SubAck, Throwable throwable) {
        if (throwable != null) {
            log.error("主题[{}]订阅失败！", mqttSubscribe.getSubscriptions()
                    .stream()
                    .map(mqttSubscription -> mqttSubscription.getTopicFilter()
                            .getTopicFilterString())
                    .toArray(), throwable);
        } else {
            ImmutableList<MqttSubscription> subscriptions = mqttSubscribe.getSubscriptions();
            if (!subscriptions.isEmpty()) {
                MqttSubscription mqttSubscription = subscriptions.get(0);
                List<? extends Mqtt5UserProperty> mqtt5UserProperties = mqtt5SubAck.getUserProperties()
                        .asList();
                String username = new String(mqtt5UserProperties.get(0)
                        .getName()
                        .toByteBuffer()
                        .array());
                if ("/chatRoom".equals(mqttSubscription.getTopicFilter()
                        .getTopicFilterString())) {
                    defaultMqtt5Client.publish("/chatRoom", "欢迎".concat(username) + "加入聊天室!");
                    return;
                }
            }
            log.info("主题[{}]订阅成功!", mqttSubscribe.getSubscriptions()
                    .stream()
                    .map(mqttSubscription -> mqttSubscription.getTopicFilter()
                            .getTopicFilterString())
                    .toArray());
        }
    }

}
