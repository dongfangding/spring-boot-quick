package com.ddf.boot.quick.mqtt;

import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 默认实现$
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
 * @date 2019/12/25 0025 15:30
 */
@Slf4j
public class DefaultMqttEventListener implements MqttEventListener {

    /**
     * 主题的订阅事件，接收到消息成功还是失败
     */
    private MqttSubscribeEvent mqttSubscribeEvent;

    public DefaultMqttEventListener(MqttSubscribeEvent mqttSubscribeEvent) {
        this.mqttSubscribeEvent = mqttSubscribeEvent;
    }


    /**
     * 服务初始化完成，
     * 在这里可以初始化所有的订阅事件
     *
     * @param mqttClientConnectedContext
     * @param defaultMqtt5Client
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 15:19
     **/
    @Override
    public void connected(MqttClientConnectedContext mqttClientConnectedContext, @NotNull DefaultMqtt5Client defaultMqtt5Client) {
        MqttProperties mqttProperties = defaultMqtt5Client.getMqttProperties();
        if (mqttProperties != null) {
            List<MqttProperties.SubscribeDetail> subscribeDetails = mqttProperties.getSubscribeDetails();
            if (subscribeDetails != null && !subscribeDetails.isEmpty()) {
                for (MqttProperties.SubscribeDetail subscribeDetail : subscribeDetails) {
                    defaultMqtt5Client.getMqtt5Client().toAsync().subscribeWith().topicFilter(subscribeDetail.getTopic())
                            .qos(subscribeDetail.getQos()).callback(publish -> {
                        mqttSubscribeEvent.onSuccess(publish);
                    }).send().whenComplete((subAck, throwable) -> {
                        if (throwable != null) {
                            mqttSubscribeEvent.onFailure(subAck, throwable);
                        }
                    });
                }
            }
        }
        // 要等所有的订阅都注册完成之后才能算初始化完成，不然这个时候发布消息会出现丢失的问题
        defaultMqtt5Client.setConnAck(true);
    }

    /**
     * 服务连接关闭
     *
     * @param mqttClientDisconnectedContext
     * @param defaultMqtt5Client
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 15:25
     **/
    @Override
    public void disconnected(MqttClientDisconnectedContext mqttClientDisconnectedContext, DefaultMqtt5Client defaultMqtt5Client) {

    }

    /**
     * 异常
     *
     * @param mqtt5ConnAck
     * @param throwable
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 15:25
     **/
    @Override
    public void handlerThrowable(Mqtt5ConnAck mqtt5ConnAck, Throwable throwable) {

    }
}
