package com.ddf.boot.quick.mqtt;

import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;

/**
 * 消息订阅事件$
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
 * @date 2019/12/25 0025 15:41
 */
public interface MqttPublishSubscribeEvent {

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
    void onPublishComplete(DefaultMqtt5Client defaultMqtt5Client, Mqtt5PublishResult mqtt5PublishResult);

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
    void onSubscribeCallback(DefaultMqtt5Client defaultMqtt5Client, Mqtt5Publish mqtt5Publish);


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
    void onSubscribeComplete(DefaultMqtt5Client defaultMqtt5Client, MqttSubscribe mqttSubscribe,
            Mqtt5SubAck mqtt5SubAck, Throwable throwable);
}
