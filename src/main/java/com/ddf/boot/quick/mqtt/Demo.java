package com.ddf.boot.quick.mqtt;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

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
 * @date 2019/12/24 0024 17:29
 */
@Slf4j
public class Demo {

    public static void main(String[] args) throws InterruptedException, MqttException {

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName("ddf");
        mqttConnectOptions.setPassword("123456".toCharArray());
        MemoryPersistence persistence = new MemoryPersistence();
        MqttAsyncClient mqttAsyncClient = new MqttAsyncClient("tcp://localhost:1883", IdUtil.objectId(), persistence);

        IMqttToken connect = mqttAsyncClient.connect(mqttConnectOptions, "init", new IMqttActionListenerImpl());
        connect.waitForCompletion();
        IMqttToken subscribe = connect.getClient().subscribe("/demo", 2);
        subscribe.setUserContext("subscribe");
        subscribe.setActionCallback(new IMqttActionListenerImpl());
        IMqttDeliveryToken publish = connect.getClient().publish("/demo", new MqttMessage("haha".getBytes()));
        publish.setUserContext("publish");
        publish.setActionCallback(new IMqttActionListenerImpl());

    }

    @Slf4j
    static class IMqttActionListenerImpl implements IMqttActionListener {

        /**
         * This method is invoked when an action has completed successfully.
         *
         * @param asyncActionToken associated with the action that has completed
         */
        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            if ("init".equals(asyncActionToken.getUserContext())) {
                log.info("连接成功>>>>>>>>");
            } else if ("publish".equals(asyncActionToken.getUserContext())) {
                log.info("发布成功！");
            } else if ("subscribe".equals(asyncActionToken.getUserContext())) {
                try {
                    log.info("订阅到消息: {}", new String(asyncActionToken.getResponse().getPayload()));
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * This method is invoked when an action fails.
         * If a client is disconnected while an action is in progress
         * onFailure will be called. For connections
         * that use cleanSession set to false, any QoS 1 and 2 messages that
         * are in the process of being delivered will be delivered to the requested
         * quality of service next time the client connects.
         *
         * @param asyncActionToken associated with the action that has failed
         * @param exception        thrown by the action that has failed
         */
        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            log.info("连接失败" );
        }
    }
}
