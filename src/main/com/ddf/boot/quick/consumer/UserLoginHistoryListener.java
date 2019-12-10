package com.ddf.boot.quick.consumer;

import com.ddf.boot.common.mq.definition.BindingConst;
import com.ddf.boot.common.mq.definition.MqMessageWrapper;
import com.ddf.boot.common.mq.definition.QueueBuilder;
import com.ddf.boot.common.mq.helper.RabbitTemplateHelper;
import com.ddf.boot.common.mq.util.MqMessageUtil;
import com.ddf.boot.quick.model.dto.LogUserLoginHistoryDto;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 用户登录日志消费
 *
 * @author dongfang.ding
 * @date 2019/12/10 0010 21:59
 */
@Slf4j
@Component
public class UserLoginHistoryListener {

    @RabbitListener(queues = BindingConst.QueueName.USER_LOGIN_HISTORY_QUEUE, containerFactory = "manualAckRabbitListenerContainerFactory")
    @RabbitHandler
    public void consumer(@Payload String json, Channel channel, Message message) {
        log.info("开始消费登录日志。。。。。。。。。。。。。。");
        MqMessageWrapper<LogUserLoginHistoryDto> parse = null;
        try {
            parse = MqMessageUtil.parse(message, LogUserLoginHistoryDto.class);
            log.info("消费到消息内容: {}", parse);
            if (true) {
                throw new RuntimeException();
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("消息消费异常！ {}", MqMessageUtil.getBodyAsString(message.getBody()), e);
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                if (parse != null) {
                    parse.setRetryTimes(parse.getRetryTimes() + 1);
                    RabbitTemplateHelper.sendNotNecessary(QueueBuilder.QueueDefinition.USER_LOGIN_HISTORY_QUEUE, parse);
                }
            } catch (IOException ex) {
                log.error("消息拒绝异常！{}", json, e);
            }
        }
    }
}
