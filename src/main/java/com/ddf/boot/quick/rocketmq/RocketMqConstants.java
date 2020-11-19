package com.ddf.boot.quick.rocketmq;

/**
 * <p>用来存储RocketMQ使用期间需要定义的常量</p >
 *
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/11/19 19:16
 */
public interface RocketMqConstants {


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
     * 应保持Topic的通用性，使用tag来筛选数据, 这可以不用建立太多的topic
     *
     * https://github.com/apache/rocketmq/blob/master/docs/cn/best_practice.md#1--tags%E7%9A%84%E4%BD%BF%E7%94%A8
     */
    interface Tag {

        String STRING = "string";
    }

    /**
     * 消费组
     */
    interface ConsumerGroup {

        String DEMO_STRING_CONSUMER_GROUP = "demo_consumer_group";

    }



}
