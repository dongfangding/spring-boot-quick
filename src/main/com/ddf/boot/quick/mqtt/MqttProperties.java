package com.ddf.boot.quick.mqtt;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性类$
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
 * @date 2019/12/25 0025 15:42
 */
@Data
@Accessors(chain = true)
public class MqttProperties {

    private List<SubscribeDetail> subscribeDetails;

    private String serverHost;

    private int serverPort;

    private String username;

    private String password;


    @Data
    @Accessors(chain = true)
    public static class SubscribeDetail {
        private String topic;
        private MqttQos qos;

        public SubscribeDetail(String topic, MqttQos qos) {
            this.topic = topic;
            this.qos = qos;
        }
    }

    public static SubscribeDetail createSubscribeDetail(String topic, MqttQos qos) {
        return new SubscribeDetail(topic, qos);
    }

    public static class SubscribeDetailBuilder {
        private static List<SubscribeDetail> list;

        public static SubscribeDetailBuilder create(int size) {
            list = new ArrayList<>(size);
            return new SubscribeDetailBuilder();
        }

        public SubscribeDetailBuilder add(SubscribeDetail subscribeDetail) {
            list.add(subscribeDetail);
            return this;
        }

        public SubscribeDetailBuilder add(String topic, MqttQos qos) {
            list.add(createSubscribeDetail(topic, qos));
            return this;
        }

        public void toProperties(MqttProperties mqttProperties) {
            mqttProperties.setSubscribeDetails(list);
        }
    }
}
