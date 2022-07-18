package com.ddf.boot.quick.features.mq;

import com.ddf.boot.common.core.util.JsonUtil;
import com.ddf.boot.common.core.util.MailUtil;
import com.ddf.boot.common.core.util.StringExtUtil;
import com.ddf.boot.common.mq.definition.MqMessageWrapper;
import com.ddf.boot.common.mq.definition.QueueBuilder;
import com.ddf.boot.common.mq.listener.MqEventListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * 用于提醒的mq消费事件$
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
 * @date 2019/12/24 0024 10:20
 */
@Component
public class NoticeMqEventListener implements MqEventListener {

    @Autowired
    private MailUtil mailUtil;
    @Autowired
    @Qualifier("mailSendExecutor")
    private ThreadPoolTaskExecutor mailSendExecutor;

    /**
     * 消息发送失败时的回调事件
     *
     * @param queueDefinition
     * @param messageWrapper
     * @param throwable
     * @return void
     * @author dongfang.ding
     * @date 2019/12/16 0016 17:50
     **/
    @Override
    public <T> void sendFailure(QueueBuilder.QueueDefinition queueDefinition, MqMessageWrapper<T> messageWrapper,
            Throwable throwable) {
        mailSendExecutor.execute(() -> {
            mailUtil.sendMimeMail(
                    new String[] {"1041765757@qq.com"},
                    String.format("mq消息[%s]发送失败提醒", messageWrapper.getMessageId()),
                    JsonUtil.asString(messageWrapper) + "<br /><br /> <font color='red'>"
                            + StringExtUtil.exceptionToStringNoLimit(throwable) + "</font>"
            );
        });
    }

    /**
     * 消息消息失败时的回调事件
     *
     * @param rabbitListener
     * @param messageWrapper
     * @param throwable
     * @return void
     * @author dongfang.ding
     * @date 2019/12/16 0016 17:50
     **/
    @Override
    public <T> void consumerFailure(RabbitListener rabbitListener, MqMessageWrapper<T> messageWrapper,
            Throwable throwable) {
        mailSendExecutor.execute(() -> {
            mailUtil.sendMimeMail(
                    new String[] {"1041765757@qq.com"},
                    String.format("mq消息[%s]消费失败提醒", messageWrapper.getMessageId()),
                    JsonUtil.asString(messageWrapper) + "<br /><br /> <font color='red'>"
                            + StringExtUtil.exceptionToStringNoLimit(throwable) + "</font>"
            );
        });
    }
}
