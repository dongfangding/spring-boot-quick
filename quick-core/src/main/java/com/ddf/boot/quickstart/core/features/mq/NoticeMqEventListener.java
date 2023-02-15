package com.ddf.boot.quickstart.core.features.mq;

import com.ddf.boot.common.mq.definition.MqMessageWrapper;
import com.ddf.boot.common.mq.definition.QueueBuilder;
import com.ddf.boot.common.mq.listener.MqEventListener;
import com.ddf.boot.quickstart.core.client.MailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NoticeMqEventListener implements MqEventListener {

    private final MailClient mailClient;

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
        mailClient.sendRabbitMQConsumeFailureMail(messageWrapper, throwable);
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
        mailClient.sendRabbitMQConsumeFailureMail(messageWrapper, throwable);
    }
}
