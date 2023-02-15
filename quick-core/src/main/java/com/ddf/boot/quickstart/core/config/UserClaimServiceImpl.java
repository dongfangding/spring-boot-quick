package com.ddf.boot.quickstart.core.config;

import com.ddf.boot.common.api.exception.BusinessException;
import com.ddf.boot.common.authentication.interfaces.UserClaimService;
import com.ddf.boot.common.authentication.model.UserClaim;
import com.ddf.boot.common.core.util.WebUtil;
import com.ddf.boot.quickstart.api.enume.ApplicationExceptionCode;
import com.ddf.boot.quickstart.api.enume.UserStatusEnum;
import com.ddf.boot.quickstart.core.entity.UserInfo;
import com.ddf.boot.quickstart.core.repository.impl.UserInfoRepository;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>认证模块用户认证接口</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/26 23:06
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class UserClaimServiceImpl implements UserClaimService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public void storeRequest(HttpServletRequest request, String host) {

    }

    @Override
    public UserClaim getStoreUserInfo(UserClaim userClaim) {
        final UserInfo userInfo = userInfoRepository.getById(Long.parseLong(userClaim.getUserId()));
        if (Objects.isNull(userInfo)) {
            throw new BusinessException(ApplicationExceptionCode.ACCOUNT_NOT_EXISTS);
        }
        if (Objects.equals(UserStatusEnum.BLACK.name(), userInfo.getStatus())) {
            throw new BusinessException(ApplicationExceptionCode.ACCOUNT_IN_BLACK);
        }
        userClaim.setUserId(userInfo.getId().toString());
        userClaim.setUsername(userInfo.getNickname());
        userClaim.setCredit(WebUtil.getUserAgent());
        return userClaim;
    }
}
