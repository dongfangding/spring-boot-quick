package com.ddf.boot.quick.features.mq.consumer;

import com.ddf.boot.common.core.exception200.ServerErrorException;
import com.ddf.boot.common.mq.definition.BindingConst;
import com.ddf.boot.common.mq.definition.MqMessageWrapper;
import com.ddf.boot.common.mq.definition.QueueBuilder;
import com.ddf.boot.common.mq.helper.MqMessageHelper;
import com.ddf.boot.common.mq.helper.RabbitTemplateHelper;
import com.ddf.boot.quick.features.mongo.collection.UserLoginHistoryCollection;
import com.ddf.boot.quick.features.mongo.repository.UserLoginHistoryCollectionRepository;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户登录日志消费$
 *
 * @author dongfang.ding
 * @date 2020/9/19 0019 13:55
 */
@Slf4j
@Component
public class UserLoginHistoryConsumer {

    @Autowired
    private MqMessageHelper mqMessageHelper;

    @Autowired
    private RabbitTemplateHelper rabbitTemplateHelper;

    @Autowired
    private UserLoginHistoryCollectionRepository userLoginHistoryCollectionRepository;

    /**
     * 演示手动ack模式，消费成功后确认消息，消费失败后，拒绝消息，拒绝成功后然后执行自实现的消息简易消息重投
     * <p>
     * 该种模式下，要消费的队列，不要配置死信队列；其实问题不是出在不能配置，而是配置后如果有消费的话，每次失败重投消息都会转发到 另外一个接收队列中，导致消息重复！详见{@link
     * RabbitTemplateHelper#requeue(com.ddf.boot.common.mq.definition.QueueBuilder.QueueDefinition,
     * com.ddf.boot.common.mq.definition.MqMessageWrapper, java.util.function.Consumer)}
     *
     * @param channel
     * @param message
     * @return void
     * @author dongfang.ding
     * @date 2019/12/16 0016 15:56
     **/
    @RabbitListener(queues = BindingConst.QueueName.USER_LOGIN_HISTORY_QUEUE, containerFactory = BindingConst.ACK_MODE_SINGLE_MANUAL_ACK)
    @RabbitHandler
    public void consumerUserLoginHistory(Channel channel, Message message) {
        log.info("开始消费[{}]登录日志>>>>", BindingConst.QueueName.USER_LOGIN_HISTORY_QUEUE);
        MqMessageWrapper<UserLoginHistoryCollection> parse = null;
        try {
            parse = mqMessageHelper.parse(message, UserLoginHistoryCollection.class);
            log.info("消费到消息内容: {}", parse);
            userLoginHistoryCollectionRepository.save(parse.getBody());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("消息消费异常！ {}", parse, e);
            rabbitTemplateHelper.nackAndRequeue(
                    channel, message, QueueBuilder.QueueDefinition.USER_LOGIN_HISTORY_QUEUE, parse);
            // catch之后必须再把异常throw出去，否则无法捕捉到消费异常
            throw new ServerErrorException(e.getMessage());
        }
    }
}
