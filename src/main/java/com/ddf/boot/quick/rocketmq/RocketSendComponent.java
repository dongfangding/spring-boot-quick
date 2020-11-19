package com.ddf.boot.quick.rocketmq;

import lombok.AllArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rocketmq发送方
 *
 * @author dongfang.ding
 * @date 2020/11/8 0008 22:00
 */
@Component
@AllArgsConstructor(onConstructor_={@Autowired})
public class RocketSendComponent {

    private final RocketMQTemplate rocketMQTemplate;


    public void sendMsgDemo() {

    }

}
