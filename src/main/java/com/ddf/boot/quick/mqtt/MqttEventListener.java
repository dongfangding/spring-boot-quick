package com.ddf.boot.quick.mqtt;

import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;

/**
 * mqtt初始化事件$
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
 * @date 2019/12/25 0025 15:18
 */
public interface MqttEventListener {

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
    void connected(MqttClientConnectedContext mqttClientConnectedContext, DefaultMqtt5Client defaultMqtt5Client);

    /**
     * 服务连接关闭
     *
     * @param mqttClientDisconnectedContext
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 15:25
     **/
    void disconnected(MqttClientDisconnectedContext mqttClientDisconnectedContext, DefaultMqtt5Client defaultMqtt5Client);


    /**
     * 异常
     *
     * @param mqtt5ConnAck
     * @param throwable
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 15:25
     **/
    void handlerThrowable(Mqtt5ConnAck mqtt5ConnAck, Throwable throwable);
}
