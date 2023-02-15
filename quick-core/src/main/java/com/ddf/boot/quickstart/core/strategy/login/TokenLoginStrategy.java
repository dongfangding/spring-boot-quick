package com.ddf.boot.quickstart.core.strategy.login;

import com.ddf.boot.common.authentication.model.AuthenticateCheckResult;
import com.ddf.boot.common.authentication.model.UserClaim;
import com.ddf.boot.common.authentication.util.TokenUtil;
import com.ddf.boot.quickstart.api.enume.LoginTypeEnum;
import com.ddf.boot.quickstart.api.request.auth.LoginRequest;
import com.ddf.boot.quickstart.core.entity.UserInfo;
import com.ddf.boot.quickstart.core.repository.impl.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>使用token登录， 校验token， 续token</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/17 11:54
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class TokenLoginStrategy implements LoginStrategy {

    private final UserInfoRepository userInfoRepository;

    @Override
    public LoginTypeEnum getLoginType() {
        return LoginTypeEnum.TOKEN;
    }

    @Override
    public UserInfo login(LoginRequest loginRequest) {
        final String token = loginRequest.getCredential();
        // 校验并且解析token
        final AuthenticateCheckResult checkResult = TokenUtil.checkToken(token);
        final UserClaim userClaim = checkResult.getUserClaim();
        final String userId = userClaim.getUserId();
        // 刷新token
        TokenUtil.refreshToken(userId, token);
        return userInfoRepository.getById(Long.parseLong(userId));
    }
}
