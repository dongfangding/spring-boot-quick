package com.ddf.boot.quick.features.mqtt;

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
 * @date 2019/12/26 0026 13:12
 */
@Slf4j
public class Mqtt5ClientFactory {

    private static volatile DefaultMqtt5Client defaultMqtt5Client;

    public static DefaultMqtt5Client getInstance(MqttProperties mqttProperties, MqttEventListener mqttEventListener) {
        if (defaultMqtt5Client == null) {
            synchronized (DefaultMqtt5Client.class) {
                if (defaultMqtt5Client == null) {
                    defaultMqtt5Client = new DefaultMqtt5Client(mqttProperties, mqttEventListener);
                }
            }
        }
        defaultMqtt5Client.waitConnected();
        return defaultMqtt5Client;
    }

    public static DefaultMqtt5Client getInstance() {
        if (!defaultMqtt5Client.isConnAck()) {
            log.error("客户端尚未初始化完成!必须调用有参的构造函数,对客户端进行初始化!");
            throw new RuntimeException("");
        }
        return defaultMqtt5Client;
    }

    public static DefaultMqtt5Client create(MqttProperties mqttProperties, MqttEventListener mqttEventListener) {
        return new DefaultMqtt5Client(mqttProperties, mqttEventListener);
    }
}
