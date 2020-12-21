package com.ddf.boot.quick.mqtt;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.IdUtil;
import com.hivemq.client.internal.mqtt.MqttClientExecutorConfigImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientBuilder;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PayloadFormatIndicator;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

/**
 * https://mosquitto.org/man/mosquitto-conf-5.html
 * <p>
 * 要修改的几个配置点
 * <p>
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
     * Mqtt的客户端
     */
    private Mqtt5Client mqtt5Client;

    /**
     * 连接建立相关事件
     */
    private MqttEventListener mqttEventListener;

    /**
     * 订阅相关事件
     */
    private List<MqttPublishSubscribeEvent> mqttPublishSubscribeEvents;

    /**
     * 连接属性
     */
    private MqttProperties mqttProperties;

    /**
     * 初始化的线程对该字段赋值，要保证对其它发布线程立马可见
     */
    private volatile boolean connAck;

    public DefaultMqtt5Client(@NotNull MqttProperties mqttProperties, @Nullable MqttEventListener mqttEventListener) {
        this.mqttProperties = mqttProperties;
        this.mqttEventListener = mqttEventListener;
        init();
    }


    /**
     * 添加发布与订阅事件实现
     *
     * @param mqttPublishSubscribeEvent
     * @return void
     * @author dongfang.ding
     * @date 2019/12/26 0026 10:47
     **/
    public void addMqttSubscribeEvent(MqttPublishSubscribeEvent mqttPublishSubscribeEvent) {
        if (mqttPublishSubscribeEvents == null) {
            mqttPublishSubscribeEvents = new ArrayList<>();
        }
        mqttPublishSubscribeEvents.add(mqttPublishSubscribeEvent);
    }

    /**
     * 校验参数
     *
     * @return void
     * @author dongfang.ding
     * @date 2019/12/26 0026 10:47
     **/
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
                .serverHost(mqttProperties.getServerHost())
                .serverPort(mqttProperties.getServerPort())

                // --------------------------------监听器配置---------------------------------------------
                .addConnectedListener(mqttClientConnectedContext -> {
                    if (mqttEventListener != null) {
                        mqttEventListener.connected(mqttClientConnectedContext, this);
                    }
                })
                .addDisconnectedListener(mqttClientDisconnectedContext -> {
                    if (mqttEventListener != null) {
                        mqttEventListener.disconnected(mqttClientDisconnectedContext, this);
                    }
                })

                // ---------------------------------线程配置----------------------------------------------
                // 一个注释也没有，nettyExecutor好像时用来配置Netty的线程池， applicationScheduler是用来配置mqtt相关的异步线程池，
                // 如异步监听，发送回调，订阅回调， nettyThreads看不出来是干嘛的
                .executorConfig(MqttClientExecutorConfigImpl.DEFAULT.extend()
                        // 这应该不是核心数，不然下面就不会再让配置线程池了吧，还是说最大线程数？搞不清楚，不动了
                        //                        .nettyThreads()
                        .nettyExecutor(new ThreadPoolExecutor(Runtime.getRuntime()
                                .availableProcessors(), Runtime.getRuntime()
                                .availableProcessors() * 2, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000),
                                ThreadFactoryBuilder.create()
                                        .setNamePrefix("mqtt-netty-executor-")
                                        .build()
                        ))
                        .build())


                // 重连配置，使用默认，最小延迟1秒，最大120秒
                .automaticReconnectWithDefaultConfig();
        // 构造一个异步的客户端
        Mqtt5AsyncClient mqtt5AsyncClient = clientBuilder.useMqttVersion5()
                .buildAsync();
        // 连接属性配置
        mqtt5AsyncClient.connectWith()
                .sessionExpiryInterval(120)
                .keepAlive(60)
                // 简单授权，需要配置密码文件，参考https://mosquitto.org/man/mosquitto_passwd-1.html
                .simpleAuth()
                .username(mqttProperties.getUsername())
                .password(mqttProperties.getPassword()
                        .getBytes())
                .applySimpleAuth()

                // -------------------------------------------------Will Publish------------------------------------------
                // 当客户端不正确断开或者原因代码为DISCONNECT_WITH_WILL_MESSAGE时，会由代理发布一条消息
                // 发布主题/消息质量qos/消息内容
                .willPublish()
                .topic("/will")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload("发布遗嘱".getBytes())
                // retain 是否保留消息，如果保留的话，客户端在发布后再订阅依然会收到保留的最后一条消息/
                // messageExpiryInterval 消息的过期时间
                // delayInterval 消息延迟发布
                .retain(true)
                .messageExpiryInterval(100)
                .delayInterval(10)
                .payloadFormatIndicator(Mqtt5PayloadFormatIndicator.UTF_8)
                .contentType("text/plain")
                .responseTopic("response/topic")
                .correlationData("correlationData".getBytes())
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
                .send()
                .whenComplete((connAck, throwable) -> {
                    if (throwable != null) {
                        if (mqttEventListener != null) {
                            mqttEventListener.handlerThrowable(connAck, throwable);
                        }
                    }
                });
        mqtt5Client = mqtt5AsyncClient;
    }


    /**
     * 由于客户端的建立使用的是异步的API,因此可能在客户端还没初始化完成时，就调用了客户端的方法，那么肯定会失败；
     * 目前不强制在调用方法前进行校验是否已完成，没完成就循环等待；而是提供一个方法，让调用方明确
     *
     * @return void
     * @author dongfang.ding
     * @date 2019/12/25 0025 16:38
     **/
    public void waitConnected() {
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
    public void waitConnected(long millionSecond) {
        long before = System.currentTimeMillis();
        while (!connAck) {
            if (System.currentTimeMillis() - before > millionSecond) {
                break;
            }
        }
    }

    public void publish(String topic, String message) {
        publish(topic, message, false);
    }

    public void publish(String topic, String message, MqttUserPropertiesImpl mqttUserProperties) {
        publish(topic, message, false, MqttQos.EXACTLY_ONCE, mqttUserProperties);
    }


    public void publish(String topic, String message, boolean retain) {
        publish(topic, message, retain, MqttQos.EXACTLY_ONCE);
    }

    public void publish(String topic, String message, boolean retain, MqttQos qos) {
        publish(topic, message, retain, qos, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    /**
     * 发布主题，并触发发布事件
     *
     * @param topic
     * @param message
     * @param retain
     * @param qos
     * @return void
     * @author dongfang.ding
     * @date 2019/12/26 0026 10:46
     **/
    public void publish(String topic, String message, boolean retain, MqttQos qos,
            MqttUserPropertiesImpl mqttUserProperties) {
        MqttPublish mqttPublish = new MqttPublish(MqttTopicImpl.of(topic), ByteBuffer.wrap(message.getBytes()), qos,
                retain, 0, Mqtt5PayloadFormatIndicator.UTF_8, MqttUtf8StringImpl.of("application/json;charset=utf-8"),
                null, null, mqttUserProperties
        );
        mqtt5Client.toAsync()
                .publish(mqttPublish);
    }


    public void publish(MqttPublish mqttPublish) {
        mqtt5Client.toAsync()
                .publish(mqttPublish)
                .whenComplete((publish, throwable) -> {
                    if (mqttPublishSubscribeEvents != null && !mqttPublishSubscribeEvents.isEmpty()) {
                        for (MqttPublishSubscribeEvent mqttPublishSubscribeEvent : mqttPublishSubscribeEvents) {
                            mqttPublishSubscribeEvent.onPublishComplete(this, publish);
                        }
                    }
                });
    }


    public void subscribe(String topic) {
        subscribe(topic, MqttQos.EXACTLY_ONCE);
    }

    public void subscribe(String topic, MqttQos qos) {
        subscribe(topic, qos, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    /**
     * 订阅主题，并触发订阅事件
     *
     * @param topic
     * @param qos
     * @return void
     * @author dongfang.ding
     * @date 2019/12/26 0026 10:41
     **/
    public void subscribe(String topic, MqttQos qos, MqttUserPropertiesImpl mqtt5UserProperties) {
        MqttSubscription mqttSubscription = new MqttSubscription(MqttTopicFilterImpl.of(topic), qos,
                MqttSubscription.DEFAULT_NO_LOCAL, MqttSubscription.DEFAULT_RETAIN_HANDLING,
                MqttSubscription.DEFAULT_RETAIN_AS_PUBLISHED
        );
        ImmutableList<MqttSubscription> subscriptions = ImmutableList.of(mqttSubscription);
        // 该类的构造函数使用的是具体实现类,MqttUserPropertiesImpl,所以方法上也只能使用具体实现类了
        MqttSubscribe mqttSubscribe = new MqttSubscribe(subscriptions, mqtt5UserProperties);
        subscribe(mqttSubscribe);
    }

    public void subscribe(MqttSubscribe mqttSubscribe) {
        mqtt5Client.toAsync()
                .subscribe(mqttSubscribe, publish -> {
                    if (mqttPublishSubscribeEvents != null && !mqttPublishSubscribeEvents.isEmpty()) {
                        for (MqttPublishSubscribeEvent mqttPublishSubscribeEvent : mqttPublishSubscribeEvents) {
                            mqttPublishSubscribeEvent.onSubscribeCallback(this, publish);
                        }
                    }
                })
                .whenComplete((subAck, throwable) -> {
                    if (mqttPublishSubscribeEvents != null && !mqttPublishSubscribeEvents.isEmpty()) {
                        for (MqttPublishSubscribeEvent mqttPublishSubscribeEvent : mqttPublishSubscribeEvents) {
                            mqttPublishSubscribeEvent.onSubscribeComplete(this, mqttSubscribe, subAck, throwable);
                        }
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
        mqttProperties.setServerHost("localhost")
                .setServerPort(1883)
                .setUsername("ddf")
                .setPassword("123456");

        MqttProperties.SubscribeDetailBuilder.create(20)
                .add("/demo", MqttQos.EXACTLY_ONCE)
                .add("/demo1", MqttQos.EXACTLY_ONCE)
                .add("/demo2", MqttQos.EXACTLY_ONCE)
                .toProperties(mqttProperties);


        MqttPublishSubscribeEvent mqttPublishSubscribeEvent = new MqttPublishSubscribeEventImpl();

        MqttEventListener mqttEventListener = new DefaultMqttEventListener();

        DefaultMqtt5Client defaultMqtt5Client = Mqtt5ClientFactory.getInstance(mqttProperties, mqttEventListener);

        defaultMqtt5Client.addMqttSubscribeEvent(mqttPublishSubscribeEvent);

        //        testBase(defaultMqtt5Client);
        //        chatRoom(defaultMqtt5Client);
        concurrentPublish(defaultMqtt5Client);
    }

    public static void testBase(DefaultMqtt5Client defaultMqtt5Client) throws InterruptedException {
        defaultMqtt5Client.publish("/demo", "hello-world!".concat(IdUtil.objectId()));

        Thread.sleep(5000);

        // 模拟接口动态订阅
        defaultMqtt5Client.subscribe("/sub2");

        Thread.sleep(5000);
        defaultMqtt5Client.publish("/demo", "模拟接口调用!".concat(IdUtil.objectId()));
        defaultMqtt5Client.publish("/sub2", "模拟接口调用!".concat(IdUtil.objectId()));

        DefaultMqtt5Client defaultMqtt5Client1 = Mqtt5ClientFactory.create(
                defaultMqtt5Client.getMqttProperties(), defaultMqtt5Client.getMqttEventListener());
        defaultMqtt5Client.publish("/sub2", "模拟另外一个客户端向之前的客户端发送主题!".concat(IdUtil.objectId()));
    }

    public static void chatRoom(DefaultMqtt5Client defaultMqtt5Client) throws InterruptedException {

        defaultMqtt5Client.subscribe("/chatRoom", MqttQos.EXACTLY_ONCE,
                MqttUserPropertiesImpl.of(ImmutableList.of(MqttUserPropertyImpl.of("username", "小红")))
        );

        defaultMqtt5Client.publish("/chatRoom", "大家好!我是小红!",
                MqttUserPropertiesImpl.of(ImmutableList.of(MqttUserPropertyImpl.of("username", "小红")))
        );

        Thread.sleep(5000);

        defaultMqtt5Client.publish("/chatRoom", "大家好!我是小蓝!",
                MqttUserPropertiesImpl.of(ImmutableList.of(MqttUserPropertyImpl.of("username", "小蓝")))
        );
    }

    public static void concurrentPublish(DefaultMqtt5Client defaultMqtt5Client) {
        defaultMqtt5Client.subscribe("/concurrent", MqttQos.AT_LEAST_ONCE);

        for (int i = 0; i < 1000; i++) {

            // fixme 发送数量与订阅到的数量对应不上
            new Thread(() -> {
                defaultMqtt5Client.publish("/concurrent", "并发发送!".concat(IdUtil.objectId()));
            }).start();
        }
    }
}
