package com.ddf.boot.quickstart.core.controller.features;


import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.ddf.boot.common.core.util.IdsUtil;
import com.ddf.boot.common.rocketmq.dto.RocketMQDestination;
import com.ddf.boot.common.rocketmq.helper.RocketMQHelper;
import com.ddf.boot.quickstart.api.consts.RocketMQConstants;
import com.ddf.boot.quickstart.core.entity.UserInfo;
import com.ddf.boot.quickstart.core.features.mongo.collection.UserLoginHistoryCollection;
import com.ddf.boot.quickstart.core.repository.impl.UserInfoRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>description</p >
 * <p>
 * https://github.com/apache/rocketmq-spring/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98
 *
 * @author Snowball
 * @version 1.0
 * @menu RocketMQ相关
 * @date 2020/11/20 13:13
 */
@RestController
@Slf4j
@RequestMapping("rocketmq")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RocketMQController {

    private final RocketMQHelper rocketMQHelper;
    private final UserInfoRepository userInfoRepository;


    /**
     * 触发rocketmq 的常用发送操作
     */
    @PostMapping("sendDemo")
    public void sendDemo() {
        // 发送最简单的string消息
        rocketMQHelper.syncSend(RocketMQDestination.builder()
            .topic(RocketMQConstants.Topic.DEMO)
            .tags(RocketMQConstants.Tags.STRING)
            .build(), "hello world");

        // 发送用户对象消息
        final UserInfo userInfo = userInfoRepository.randomUser(1);
        if (Objects.nonNull(userInfo)) {
            rocketMQHelper.syncSend(RocketMQDestination.builder()
                .topic(RocketMQConstants.Topic.DEMO)
                .tags(RocketMQConstants.Tags.USER_OBJECT)
                .build(), userInfo);
        }

        // 还是发送对象信息，但是是为了演示消费端可以获取rocketmq其它原生属性信息
        final UserLoginHistoryCollection collection = new UserLoginHistoryCollection();
        collection.setId(IdUtil.objectId()).setLoginName("test").setLoginIp(NetUtil.getLocalhostStr())
            .setLoginTime(LocalDateTime.now()).setUserId(IdsUtil.getNextStrId());
        rocketMQHelper.syncSend(RocketMQDestination.builder()
            .topic(RocketMQConstants.Topic.DEMO)
            .tags(RocketMQConstants.Tags.CONSUMER_MESSAGE_EXT)
            .build(), collection);
    }
}
