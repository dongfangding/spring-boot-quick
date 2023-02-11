package com.ddf.boot.quickstart.core.strategy.login;

import cn.hutool.core.collection.CollUtil;
import com.ddf.boot.common.api.model.common.RequestContext;
import com.ddf.boot.common.api.util.DateUtils;
import com.ddf.boot.common.authentication.model.AuthenticateToken;
import com.ddf.boot.common.authentication.model.UserClaim;
import com.ddf.boot.common.authentication.util.TokenUtil;
import com.ddf.boot.common.authentication.util.UserContextUtil;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.core.util.WebUtil;
import com.ddf.boot.quickstart.api.enume.ApplicationExceptionCode;
import com.ddf.boot.quickstart.api.enume.LoginTypeEnum;
import com.ddf.boot.quickstart.api.event.UserLoginEventPayload;
import com.ddf.boot.quickstart.api.request.sys.LoginRequest;
import com.ddf.boot.quickstart.api.response.sys.LoginResponse;
import com.ddf.boot.quickstart.core.entity.SysUser;
import com.ddf.boot.quickstart.core.event.SysUserLoginEvent;
import com.ddf.boot.quickstart.core.repository.UserMetadataConfigRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * <p>登录策略上下文</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/23 23:36
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class LoginStrategyContext implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private final UserMetadataConfigRepository userMetadataConfigRepository;
    private final Map<LoginTypeEnum, LoginStrategy> loginStrategyMap = new HashMap<>();
    private final ApplicationEventPublisher applicationEventPublisher;


    @PostConstruct
    public void init() {
        final Map<String, LoginStrategy> strategyMap = applicationContext.getBeansOfType(LoginStrategy.class);
        if (CollUtil.isEmpty(strategyMap)) {
            return;
        }
        strategyMap.forEach((beanName, clazz) -> {
            loginStrategyMap.put(clazz.getLoginType(), clazz);
        });
    }

    /**
     * 执行登录
     *
     * @param loginRequest
     * @return
     */
    public LoginResponse login(LoginRequest loginRequest) {
        final LoginTypeEnum loginType = loginRequest.getLoginType();
        final LoginStrategy loginStrategy = loginStrategyMap.get(loginType);
        PreconditionUtil.checkArgument(Objects.nonNull(loginStrategy), ApplicationExceptionCode.LOGIN_STRATEGY_MAPPING_ERROR);
        // 登录策略， 校验通过后返回的用户信息
        final SysUser sysUser = loginStrategy.login(loginRequest);

        String token = loginRequest.getCredential();
        if (loginType.shouldCreateToken()) {
            // 生成token
            final UserClaim userClaim = new UserClaim();
            userClaim.setUserId(sysUser.getId().toString());
            userClaim.setUsername(sysUser.getNickname());
            userClaim.setCredit(WebUtil.getUserAgent());
            final AuthenticateToken authenticateToken = TokenUtil.createToken(userClaim);
            token = authenticateToken.getToken();
        }
        // 发布登录事件
        final RequestContext requestContext = UserContextUtil.getRequestContext();
        final UserLoginEventPayload userLoginEventPayload = new UserLoginEventPayload();
        userLoginEventPayload.setUserId(sysUser.getId());
        userLoginEventPayload.setLoginType(loginType.name());
        userLoginEventPayload.setLoginIp(requestContext.getClientIp());
        userLoginEventPayload.setLoginTime(DateUtils.currentTimeSeconds());
        userLoginEventPayload.setImei(requestContext.getImei());
        userLoginEventPayload.setOs(requestContext.getOs().name());
        userLoginEventPayload.setLongitude(requestContext.getLongitude());
        userLoginEventPayload.setLatitude(requestContext.getLatitude());
        userLoginEventPayload.setVersion(requestContext.getVersion());
        applicationEventPublisher.publishEvent(new SysUserLoginEvent(this, userLoginEventPayload));

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
}
