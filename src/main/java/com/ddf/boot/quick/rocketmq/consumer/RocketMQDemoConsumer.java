package com.ddf.boot.quick.rocketmq.consumer;

import com.ddf.boot.common.model.datao.quick.AuthUser;
import com.ddf.boot.quick.rocketmq.RocketMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/11/20 13:18
 */
@Slf4j
@Component
public class RocketMQDemoConsumer {

    //---------------------------发送请查看com.ddf.boot.quick.controller.RocketMQController-----------------------------

    /**
     * 简单string消息
     */
    @Component
    @RocketMQMessageListener(topic = RocketMQConstants.Topic.DEMO, consumeMode = ConsumeMode.CONCURRENTLY,
        consumerGroup = RocketMQConstants.ConsumerGroup.DEMO_STRING_CONSUMER_GROUP,
        selectorExpression = RocketMQConstants.Tags.STRING)
    public static class DemoStringConsumer implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            log.info("{}接收到消息内容: {}", RocketMQConstants.ConsumerGroup.DEMO_STRING_CONSUMER_GROUP, message);
        }
    }

    /**
     * 发送用户对象消息
     */
    @Component
    @RocketMQMessageListener(topic = RocketMQConstants.Topic.DEMO, consumeMode = ConsumeMode.CONCURRENTLY,
        consumerGroup = RocketMQConstants.ConsumerGroup.DEMO_USER_CONSUMER_GROUP,
        selectorExpression = RocketMQConstants.Tags.USER_OBJECT)
    public static class DemoUserObjectConsumer implements RocketMQListener<AuthUser> {

        @Override
        public void onMessage(AuthUser message) {
            log.info("{}接收到消息内容: {}", RocketMQConstants.ConsumerGroup.DEMO_USER_CONSUMER_GROUP, message);
        }
    }

    /**
     * 发送用户对象消息
     */
    @Component
    @RocketMQMessageListener(topic = RocketMQConstants.Topic.DEMO, consumeMode = ConsumeMode.CONCURRENTLY,
        consumerGroup = RocketMQConstants.ConsumerGroup.CONSUMER_MESSAGE_EXT_CONSUMER_GROUP,
        selectorExpression = RocketMQConstants.Tags.CONSUMER_MESSAGE_EXT)
    public static class DemoConsumerMessageExt implements RocketMQListener<MessageExt> {

        @Override
        public void onMessage(MessageExt message) {
            log.info("{}接收到消息, msgId: {}, tags: {}, keys: {}, 内容: {}", RocketMQConstants.ConsumerGroup.CONSUMER_MESSAGE_EXT_CONSUMER_GROUP,
                message.getMsgId(), message.getTags(), message.getKeys(),
                new String(message.getBody()));
        }
    }
}
