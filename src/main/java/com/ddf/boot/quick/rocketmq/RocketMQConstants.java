package com.ddf.boot.quick.rocketmq;

/**
 * <p>用来存储RocketMQ使用期间需要定义的常量</p >
 *
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/11/19 19:16
 */
public interface RocketMQConstants {


    /**
     * 消息topic
     */
    interface Topic {

        /**
         * 演示Topic
         */
        String DEMO = "demo";


    }


    /**
     * 应保持Topic的通用性，使用tags来筛选数据, 这可以不用建立太多的topic
     *
     * https://github.com/apache/rocketmq/blob/master/docs/cn/best_practice.md#1--tags%E7%9A%84%E4%BD%BF%E7%94%A8
     */
    interface Tags {

        /**
         * string格式
         */
        String STRING = "string";

        /**
         * 发送用户对象
         */
        String USER_OBJECT = "user_object";


        /**
         * 演示消费端可以获取除了payload意外的其它rocketmq的原生属性
         */
        String CONSUMER_MESSAGE_EXT = "consumer_message_ext";
    }

    /**
     * 消费组
     */
    interface ConsumerGroup {

        /**
         * 消费组
         */
        String DEMO_STRING_CONSUMER_GROUP = Topic.DEMO + "_" + Tags.STRING;

        /**
         * 用户对象消费组
         */
        String DEMO_USER_CONSUMER_GROUP = Topic.DEMO + "_" + Tags.USER_OBJECT;

        /**
         * 用户对象消费组
         */
        String CONSUMER_MESSAGE_EXT_CONSUMER_GROUP = Topic.DEMO + "_" + Tags.CONSUMER_MESSAGE_EXT;

    }



}
