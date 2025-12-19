package com.ddf.boot.quickstart.core.controller;

import com.ddf.boot.common.api.model.common.response.ResponseData;
import com.ddf.boot.quickstart.core.application.UserApplicationService;
import com.ddf.boot.quickstart.core.infra.config.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>用户控制器</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:56
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserApplicationService userApplicationService;
    private final ApplicationProperties applicationProperties;

    /**
     * 心跳
     *
     * @return
     */
    @PostMapping("heartbeat")
    public ResponseData<Void> heartbeat() {
        userApplicationService.heartBeat(1L);
        return ResponseData.empty();
    }
}
