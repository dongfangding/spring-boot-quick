package com.ddf.boot.quick.biz;

import com.ddf.boot.common.mq.definition.QueueBuilder;
import com.ddf.boot.common.mq.exception.MqSendException;
import com.ddf.boot.common.mq.helper.RabbitTemplateHelper;
import com.ddf.boot.quick.model.dto.LogUserLoginHistoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务集合
 *
 * @author dongfang.ding
 * @date 2019/12/10 0010 20:18
 */
@Component
@Slf4j
public class AsyncTask {

    @Async("userLoginLogExecutor")
    public void logUserLoginHistory(LogUserLoginHistoryDto userLoginHistoryDto) {
        if (userLoginHistoryDto == null) {
            log.error("用户登录日志数据不全！");
        }
        try {
            RabbitTemplateHelper.wrapperAndSend(QueueBuilder.QueueDefinition.USER_LOGIN_HISTORY_QUEUE, userLoginHistoryDto);
        } catch (MqSendException ignored) {}

        try {
            // 发送给一个死信队列
            RabbitTemplateHelper.wrapperAndSend(QueueBuilder.QueueDefinition.TEST_DEAD_LETTER_QUEUE, userLoginHistoryDto );
        } catch (MqSendException ignored) {}

        try {
            // 发送给一个死信延迟队列
            RabbitTemplateHelper.wrapperAndSend(QueueBuilder.QueueDefinition.TEST_TTL_QUEUE, userLoginHistoryDto);
        } catch (MqSendException ignored) {}
    }


}
