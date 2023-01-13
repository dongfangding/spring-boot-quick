package com.ddf.boot.quickstart.core.controller;

import com.ddf.boot.common.api.model.common.response.response.ResponseData;
import com.ddf.boot.common.authentication.util.UserContextUtil;
import com.ddf.game.xiuxian.api.enume.PlayerConfigCodeEnum;
import com.ddf.game.xiuxian.api.request.player.LoginRequest;
import com.ddf.game.xiuxian.api.request.player.PlayerConfigSyncRequest;
import com.ddf.game.xiuxian.api.request.player.PlayerProgressSyncRequest;
import com.ddf.game.xiuxian.api.request.player.RegistryRequest;
import com.ddf.game.xiuxian.api.response.player.LoginResponse;
import com.ddf.game.xiuxian.api.response.player.PlayerProgressResponse;
import com.ddf.game.xiuxian.core.application.PlayerApplicationService;
import com.ddf.game.xiuxian.core.application.PlayerProgressApplication;
import com.ddf.game.xiuxian.core.strategy.login.LoginStrategyContext;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>玩家控制器</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:56
 */
@RestController
@RequestMapping("player")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PlayerController {

    private final PlayerApplicationService playerApplicationService;
    private final LoginStrategyContext loginStrategyContext;
    private final PlayerProgressApplication playerProgressApplication;

    /**
     * 注册账号
     *
     * @param request
     */
    @PostMapping("registry")
    public ResponseData<Void> registry(@RequestBody @Valid RegistryRequest request) {
        playerApplicationService.registry(request);
        return ResponseData.empty();
    }

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    public ResponseData<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseData.success(loginStrategyContext.login(loginRequest));
    }

    /**
     * 同步账号信息元数据
     *
     * @param request
     * @return
     */
    @PostMapping("syncAccountMetadata")
    public ResponseData<Boolean> syncAccountMetadata(@RequestBody @Valid PlayerConfigSyncRequest request) {
        return ResponseData.success(playerApplicationService.playerConfigSyncRequest(UserContextUtil.getLongUserId(),
                PlayerConfigCodeEnum.ACCOUNT_METADATA, request));
    }

    /**
     * 获取账号信息元数据
     *
     * @return
     */
    @GetMapping("getAccountMetadata")
    public ResponseData<String> getAccountMetadata() {
        return ResponseData.success(playerApplicationService.getAccountMetadata(UserContextUtil.getLongUserId()));
    }

    /**
     * 心跳
     *
     * @return
     */
    @PostMapping("heartbeat")
    public ResponseData<Void> heartbeat() {
        return ResponseData.empty();
    }


    /**
     * 玩家进度
     *
     * @return
     */
    @GetMapping("playerProgress")
    public ResponseData<PlayerProgressResponse> playerProgress() {
        return ResponseData.success(playerApplicationService.playerProgress(UserContextUtil.getLongUserId()));
    }

    /**
     * 玩家进度同步
     *
     * @return
     */
    @PostMapping("playerProgress/sync")
    public ResponseData<Boolean> playerProgressSync(@RequestBody @Valid PlayerProgressSyncRequest request) {
        return ResponseData.success(playerProgressApplication.sync(UserContextUtil.getLongUserId(), request));
    }
}
