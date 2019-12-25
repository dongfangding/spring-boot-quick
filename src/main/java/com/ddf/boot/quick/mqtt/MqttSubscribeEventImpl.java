package com.ddf.boot.quick.mqtt;

import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
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
public class MqttSubscribeEventImpl implements MqttSubscribeEvent {
    /**
     * 在创建完所有的订阅之后，会预留一个接收到数据之后的异步回调接口，在那里把数据传输到该接口中，使用方可以接收到数据之后
     * 根据不同的类型和topic去处理数据
     *
     * @param mqtt5Publish
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 15:58
     **/
    @Override
    public void onSuccess(Mqtt5Publish mqtt5Publish) {
        MqttTopic topic = mqtt5Publish.getTopic();
        // 在这里可以根据不同的topic去分发不同的类去处理业务了
        log.info("接收到[{}]的数据: {}", topic, new String(mqtt5Publish.getPayloadAsBytes()));
    }

    @Override
    public void onFailure(Mqtt5SubAck mqtt5SubAck, Throwable throwable) {

    }
}
