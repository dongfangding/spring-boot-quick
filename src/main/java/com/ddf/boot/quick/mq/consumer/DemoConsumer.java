package com.ddf.boot.quick.mq.consumer;

import com.ddf.boot.common.core.exception200.ServerErrorException;
import com.ddf.boot.common.mq.definition.BindingConst;
import com.ddf.boot.common.mq.helper.MqMessageHelper;
import com.ddf.boot.common.mq.helper.RabbitTemplateHelper;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 演示几种情况下的消费者
 *
 * @author dongfang.ding
 * @date 2019/12/10 0010 21:59
 */
@Slf4j
@Component
public class DemoConsumer {

    @Autowired
    private MqMessageHelper mqMessageHelper;

    @Autowired
    private RabbitTemplateHelper rabbitTemplateHelper;


    /**
     * 该队列是一个死信队列，消息发送该队列之后然后在这里进行消费；如果消费完成则ack,如果消费失败，该死信队列定义了转发队列， 拒绝之后并且关闭重投，消息会被转发；这里故意消费失败，拒绝消息！ 主要是为了演示{@link
     * DemoConsumer#consumerDeadLetterReceiveQueue(com.rabbitmq.client.Channel, org.springframework.amqp.core.Message)}
     *
     * @param channel
     * @param message
     * @return void
     * @author dongfang.ding
     * @date 2019/12/16 0016 16:47
     **/
    @RabbitListener(queues = BindingConst.QueueName.TEST_DEAD_LETTER_QUEUE, containerFactory = BindingConst.ACK_MODE_SINGLE_MANUAL_ACK)
    @RabbitHandler
    public void consumerDeadLetterQueue(Channel channel, Message message) {
        log.info("开始消费[{}]队列消息>>>>", BindingConst.QueueName.TEST_DEAD_LETTER_QUEUE);
        try {
            if (true) {
                throw new RuntimeException("消费失败！");
            }
            channel.basicAck(message.getMessageProperties()
                    .getDeliveryTag(), false);
        } catch (Exception e) {
            try {
                channel.basicNack(message.getMessageProperties()
                        .getDeliveryTag(), false, false);
            } catch (IOException ex) {
                log.error("拒绝失败！", e);
            }
            // catch之后必须再把异常throw出去，否则无法捕捉到消费异常
            throw new ServerErrorException(e.getMessage());
        }
    }


    /**
     * 该队列的消息由一个拒绝消息的死信队列转发而来
     *
     * @param channel
     * @param message
     * @return void
     * @author dongfang.ding
     * @date 2019/12/16 0016 16:39
     **/
    @RabbitListener(queues = BindingConst.QueueName.TEST_DEAD_LETTER_RECEIVE_QUEUE, containerFactory = BindingConst.ACK_MODE_CONCURRENT_AUTO_ACK)
    @RabbitHandler
    public void consumerDeadLetterReceiveQueue(Channel channel, Message message) {
        log.info("开始消费[{}]死信队列转发后的消息>>>>", BindingConst.QueueName.TEST_DEAD_LETTER_RECEIVE_QUEUE);
        log.info("消息到消息: {}", mqMessageHelper.getBodyAsString(message.getBody()));
    }


    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 测试延迟消费！！！！！！
     * <p>
     * 消息最初是发送给{@link BindingConst.QueueName#TEST_TTL_QUEUE}的，但是该队列被定义成了一个死信延迟队列，而且没有定义消费者，
     * 消息到达最大ttl之后就会被转发到该队列，从而达到延迟消费的目的
     *
     * @param channel
     * @param message
     * @return void
     * @author dongfang.ding
     * @date 2019/12/16 0016 16:44
     **/
    @RabbitListener(queues = BindingConst.QueueName.TEST_TTL_RECEIVE_QUEUE, containerFactory = BindingConst.ACK_MODE_SINGLE_MANUAL_ACK)
    @RabbitHandler
    public void consumerTtlReceiveQueue(Channel channel, Message message) {
        log.info("开始消费[{}]延迟队列消息>>>>", BindingConst.QueueName.TEST_TTL_RECEIVE_QUEUE);
        try {
            channel.basicAck(message.getMessageProperties()
                    .getDeliveryTag(), false);
        } catch (Exception e) {
            try {
                channel.basicNack(message.getMessageProperties()
                        .getDeliveryTag(), false, false);
            } catch (IOException ex) {
                log.error("拒绝失败！", e);
            }
            // catch之后必须再把异常throw出去，否则无法捕捉到消费异常
            throw new ServerErrorException(e.getMessage());
        }
    }
}
