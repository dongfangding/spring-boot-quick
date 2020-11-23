package com.ddf.boot.quick.controller;

import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.model.datao.quick.AuthUser;
import com.ddf.boot.common.rocketmq.dto.RocketMQDestination;
import com.ddf.boot.common.rocketmq.helper.RocketMQHelper;
import com.ddf.boot.quick.model.bo.AuthUserPageBo;
import com.ddf.boot.quick.rocketmq.RocketMQConstants;
import com.ddf.boot.quick.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>description</p >
 *
 * @menu RocketMQ相关
 * @author Snowball
 * @version 1.0
 * @date 2020/11/20 13:13
 */
@RestController
@Slf4j
@RequestMapping("rocketmq")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class RocketMQController {

    private final RocketMQHelper rocketMQHelper;

    private final AuthUserService authUserService;


    /**
     * 触发rocketmq 的常用发送操作
     */
    @PostMapping("sendDemo")
    public void sendDemo() {
        // 发送最简单的string消息
        rocketMQHelper.syncSend(RocketMQDestination.builder()
                .topic(RocketMQConstants.Topic.DEMO)
                .tags(RocketMQConstants.Tags.STRING)
                .build(),
                "hello world"
        );


        // 发送用户对象消息
        final PageResult<AuthUser> authUserPageResult = authUserService.pageList(new AuthUserPageBo());
        if (!authUserPageResult.isEmpty()) {
            rocketMQHelper.syncSend(RocketMQDestination.builder()
                            .topic(RocketMQConstants.Topic.DEMO)
                            .tags(RocketMQConstants.Tags.USER_OBJECT)
                            .build(),
                    authUserPageResult.getContent().get(0)
            );
        }

    }
}
