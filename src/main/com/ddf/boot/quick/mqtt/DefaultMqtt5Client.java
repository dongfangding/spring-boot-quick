package com.ddf.boot.quick.mqtt;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.IdUtil;
import com.hivemq.client.internal.mqtt.MqttClientExecutorConfigImpl;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientBuilder;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PayloadFormatIndicator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * https://mosquitto.org/man/mosquitto-conf-5.html
 *
 * 要修改的几个配置点
 *
 * # 持久化设置为true
 * persistence true
 * # 如果设置为 true，则在客户端套接字上设置 TCP nodelay 选项，以禁用 Nagle 的算法。 这可以减少某些消息的延迟，从而可能增加正在发送的 TCP 数据包的数量。 默认为 false。
 * set_tcp_nodelay true
 * # 作为根用户运行时，在启动时更改为此用户及其主要组。 如果mosquitto无法改变到这个用户和组，它将退出与一个错误。 如果要写入持久性数据库，指定的用户必须具有对持久性数据库的读 / 写访问权，并具有对证书、密码和 ACL 文件的读访问权。 如果以非根用户身份运行，则此设置没有效果。 默认为mosquitto。
 * user root
 * # 禁用匿名访问
 * allow_anonymous false
 * # [https://mosquitto.org/man/mosquitto_passwd-1.html](https://mosquitto.org/man/mosquitto_passwd-1.html)
 * # 文件不存在时创建，创建密码文件添加用户名和密码 mosquitto_passwd -c ./passwd ddf
 * # 文件存在时修改 mosquitto_passwd ./passwd ddf1
 * # 如果不指定的话，只要带用户名和密码好像都可以建立连接，没测试指定错误行不行
 * password_file password_file E:\mosquitto\passwd
 *
 *
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

    /**
     * 保证当前服务只提供一个实例
     */
    private static volatile DefaultMqtt5Client defaultMqtt5Client;

    /**
     * Mqtt的客户端
     */
    private Mqtt5Client mqtt5Client;

    /**
     * 连接建立相关事件
     */
    private MqttEventListener mqttEventListener;

    private MqttProperties mqttProperties;

    private volatile boolean connAck;

    public static DefaultMqtt5Client getInstance(MqttProperties mqttProperties, MqttEventListener mqttEventListener) {
        if (defaultMqtt5Client == null) {
            synchronized (DefaultMqtt5Client.class) {
                if (defaultMqtt5Client == null) {
                    defaultMqtt5Client = new DefaultMqtt5Client(mqttProperties, mqttEventListener);
                }
            }
        }
        return defaultMqtt5Client;
    }

    private DefaultMqtt5Client(MqttProperties mqttProperties, MqttEventListener mqttEventListener) {
        this.mqttProperties = mqttProperties;
        this.mqttEventListener = mqttEventListener;
        init();
    }

    public void validProperties() {
        if (mqttProperties == null) {
            throw new IllegalArgumentException("mqttProperties can not be null!");
        }
        if (StringUtils.isAnyBlank(mqttProperties.getServerHost(), mqttProperties.getServerPort() + "")) {
            throw new IllegalArgumentException("serverHost or serverPort can not be null!");
        }
        if (StringUtils.isAnyBlank(mqttProperties.getUsername(), mqttProperties.getPassword())) {
            throw new IllegalArgumentException("username or password can not be null!");
        }
    }

    public void init() {
        validProperties();
        MqttClientBuilder clientBuilder = MqttClient.builder()
                // 客户端id，如果不指定可以由服务端生成，服务端生成可以配置前缀
                .identifier(IdUtil.objectId())
                .serverHost(mqttProperties.getServerHost()).serverPort(mqttProperties.getServerPort())

                // --------------------------------监听器配置---------------------------------------------
                .addConnectedListener(mqttClientConnectedContext -> {
                    mqttEventListener.connected(mqttClientConnectedContext, this);
                })
                .addDisconnectedListener(mqttClientDisconnectedContext -> {
                    mqttEventListener.disconnected(mqttClientDisconnectedContext, this);
                })

                // ---------------------------------线程配置----------------------------------------------
                // 一个注释也没有，nettyExecutor好像时用来配置Netty的线程池， applicationScheduler是用来配置mqtt相关的异步线程池，
                // 如异步监听，发送回调，订阅回调， nettyThreads看不出来是干嘛的
                .executorConfig(MqttClientExecutorConfigImpl.DEFAULT.extend()
                        // 这应该不是核心数，不然下面就不会再让配置线程池了吧，还是说最大线程数？搞不清楚，不动了
//                        .nettyThreads()
                                .nettyExecutor(new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                                        Runtime.getRuntime().availableProcessors() * 2, 60L, TimeUnit.SECONDS,
                                        new ArrayBlockingQueue<>(1000), ThreadFactoryBuilder.create().setNamePrefix("mqtt-netty-executor-").build()))
                        .build())


                // 重连配置，使用默认，最小延迟1秒，最大120秒
                .automaticReconnectWithDefaultConfig();
        // 构造一个异步的客户端
        Mqtt5AsyncClient mqtt5AsyncClient = clientBuilder.useMqttVersion5().buildAsync();
        // 连接属性配置
        mqtt5AsyncClient.connectWith().sessionExpiryInterval(120).keepAlive(60
        )
                // 简单授权，需要配置密码文件，参考https://mosquitto.org/man/mosquitto_passwd-1.html
                .simpleAuth().username(mqttProperties.getUsername()).password(mqttProperties.getPassword().getBytes()).applySimpleAuth()

                // -------------------------------------------------Will Publish------------------------------------------
                // 当客户端不正确断开或者原因代码为DISCONNECT_WITH_WILL_MESSAGE时，会由代理发布一条消息
                // 发布主题/消息质量qos/消息内容
                .willPublish().topic("/will").qos(MqttQos.AT_LEAST_ONCE).payload("发布遗嘱".getBytes())
                // retain 是否保留消息，如果保留的话，客户端在发布后再订阅依然会收到保留的最后一条消息/
                // messageExpiryInterval 消息的过期时间
                // delayInterval 消息延迟发布
                .retain(true).messageExpiryInterval(100).delayInterval(10).payloadFormatIndicator(Mqtt5PayloadFormatIndicator.UTF_8)
                .contentType("text/plain").responseTopic("response/topic").correlationData("correlationData".getBytes())
                .userProperties()
                .add("key1", "value1")
                .add("key2", "value2")
                .applyUserProperties()
                .applyWillPublish()

                // --------------------------------------------------限制---------------------------------------------------
                .restrictions()
                // 设置消息质量为1和2，未被确认的消息的客户端允许接收的最大数量，以下值为默认值
                .receiveMaximum(Mqtt5ConnectRestrictions.DEFAULT_RECEIVE_MAXIMUM)
                // 同上，只不过是控制发送的
                .sendMaximum(Mqtt5ConnectRestrictions.DEFAULT_SEND_MAXIMUM)
                // 设置客户端从服务端接收的最大数据包大小
                .maximumPacketSize(16384)
                .sendMaximumPacketSize(16384)
                // 没看懂
                .topicAliasMaximum(0)
                .sendTopicAliasMaximum(Mqtt5ConnectRestrictions.DEFAULT_SEND_TOPIC_ALIAS_MAXIMUM)
                .requestProblemInformation(false)
                .requestResponseInformation(true)
                .applyRestrictions()
                .send().whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        mqttEventListener.handlerThrowable(connAck, throwable);
                    }
                });
        mqtt5Client = mqtt5AsyncClient;
    }


    /**
     *
     * 由于客户端的建立使用的是异步的API,因此可能在客户端还没初始化完成时，就调用了客户端的方法，那么肯定会失败；
     * 目前不强制在调用方法前进行校验是否已完成，没完成就循环等待；而是提供一个方法，让调用方明确
     *
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 16:38
     **/
    public void waitComplete() {
        while (!connAck) {
        }
    }

    /**
     * 客户端还没初始化完成时，给定一个等待时间
     *
     * @param millionSecond
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 16:39
     **/
    public void waitComplete(long millionSecond) {
        long before = System.currentTimeMillis();
        while (!connAck) {
            if (System.currentTimeMillis() - before > millionSecond) {
                break;
            }
        }
    }

    public void publish(String topic, String message) {
        mqtt5Client.toAsync().publishWith().topic(topic).payload(message.getBytes())
                .retain(true).qos(MqttQos.EXACTLY_ONCE).send().whenComplete((publish, throwable) -> {
            if (throwable != null) {
                log.error("发送失败", throwable);
            } else {
                log.info("消息发送成功>>>>>>: {}", message);
            }
        });
    }

    public boolean isConnAck() {
        return connAck;
    }

    public void setConnAck(boolean connAck) {
        this.connAck = connAck;
    }

    public Mqtt5Client getMqtt5Client() {
        return mqtt5Client;
    }

    public void setMqtt5Client(Mqtt5Client mqtt5Client) {
        this.mqtt5Client = mqtt5Client;
    }

    public MqttEventListener getMqttEventListener() {
        return mqttEventListener;
    }

    public void setMqttEventListener(MqttEventListener mqttEventListener) {
        this.mqttEventListener = mqttEventListener;
    }

    public MqttProperties getMqttProperties() {
        return mqttProperties;
    }

    public void setMqttProperties(MqttProperties mqttProperties) {
        this.mqttProperties = mqttProperties;
    }

    public static void main(String[] args) throws InterruptedException {

        MqttProperties mqttProperties = new MqttProperties();
        mqttProperties.setServerHost("localhost").setServerPort(1883).setUsername("ddf").setPassword("123456");

        MqttProperties.SubscribeDetailBuilder.create(20).add("/demo", MqttQos.EXACTLY_ONCE)
                .add("/demo1", MqttQos.EXACTLY_ONCE)
                .add("/demo2", MqttQos.EXACTLY_ONCE)
                .toProperties(mqttProperties);


        MqttSubscribeEvent mqttSubscribeEvent = new MqttSubscribeEventImpl();

        MqttEventListener mqttEventListener = new DefaultMqttEventListener(mqttSubscribeEvent);

        DefaultMqtt5Client defaultMqtt5Client = DefaultMqtt5Client.getInstance(mqttProperties, mqttEventListener);

        defaultMqtt5Client.waitComplete();
        defaultMqtt5Client.publish("/demo", "hello-world!");
    }
}
