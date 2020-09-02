package com.ddf.boot.quick.biz;

import com.ddf.boot.common.mq.helper.RabbitTemplateHelper;
import com.ddf.boot.quick.model.dto.LogUserLoginHistoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private RabbitTemplateHelper rabbitTemplateHelper;

    @Async("userLoginLogExecutor")
    public void logUserLoginHistory(LogUserLoginHistoryDto userLoginHistoryDto) {
        if (userLoginHistoryDto == null) {
            log.error("用户登录日志数据不全！");
        }
/*        try {
            rabbitTemplateHelper.wrapperAndSend(QueueBuilder.QueueDefinition.USER_LOGIN_HISTORY_QUEUE, userLoginHistoryDto);
        } catch (MqSendException ignored) {}

        try {
            // 发送给一个死信队列
            rabbitTemplateHelper.wrapperAndSend(QueueBuilder.QueueDefinition.TEST_DEAD_LETTER_QUEUE, userLoginHistoryDto );
        } catch (MqSendException ignored) {}

        try {
            // 发送给一个死信延迟队列
            rabbitTemplateHelper.wrapperAndSend(QueueBuilder.QueueDefinition.TEST_TTL_QUEUE, userLoginHistoryDto);
        } catch (MqSendException ignored) {}*/
    }


}