package com.ddf.boot.quickstart.core.strategy.login;

import com.ddf.boot.common.core.encode.BCryptPasswordEncoder;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.quickstart.api.enume.ApplicationExceptionCode;
import com.ddf.boot.quickstart.api.enume.LoginTypeEnum;
import com.ddf.boot.quickstart.api.request.sys.LoginRequest;
import com.ddf.boot.quickstart.core.entity.SysUser;
import com.ddf.boot.quickstart.core.service.ISysUserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>密码登录</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/17 11:51
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class PasswordLoginStrategy implements LoginStrategy {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ISysUserService sysUserService;

    @Override
    public LoginTypeEnum getLoginType() {
        return LoginTypeEnum.PASSWORD;
    }

    @Override
    public SysUser login(LoginRequest loginRequest) {
        final String loginName = loginRequest.getLoginIdentity();
        SysUser sysUser = sysUserService.getByLoginName(loginName);
        PreconditionUtil.checkArgument(Objects.nonNull(sysUser), ApplicationExceptionCode.ACCOUNT_NOT_EXISTS);

        String password = loginRequest.getCredential();
        final boolean matches = bCryptPasswordEncoder.matches(password, sysUser.getPassword());
        PreconditionUtil.checkArgument(matches, ApplicationExceptionCode.PASSWORD_ERROR);
        return sysUser;
    }
}
