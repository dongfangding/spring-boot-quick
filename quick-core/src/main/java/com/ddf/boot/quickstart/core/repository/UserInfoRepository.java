package com.ddf.boot.quickstart.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.quickstart.core.entity.UserInfo;
import com.ddf.boot.quickstart.core.mapper.UserInfoMapper;
import com.ddf.boot.quickstart.core.model.cqrs.user.CompleteUserInfoCommand;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>用户信息仓储/p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/21 10:53
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class UserInfoRepository {

    private final UserInfoMapper userInfoMapper;

    /**
     * 根据userId获取用户信息
     *
     * @param userId
     * @return
     */
    public UserInfo getById(Long userId) {
        return userInfoMapper.selectById(userId);
    }

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    public UserInfo getByMobile(String mobile) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getMobile, mobile);
        return userInfoMapper.selectOne(wrapper);
    }

    /**
     * 根据登录账号查询用户
     *
     * @param nickname
     * @return
     */
    public UserInfo getByAccountName(String nickname) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getNickname, nickname);
        return userInfoMapper.selectOne(wrapper);
    }

    /**
     * 昵称是否存在
     *
     * @param nickname
     * @return
     */
    public boolean nicknameExists(String nickname) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getNickname, nickname);
        return userInfoMapper.selectCount(wrapper) > 0;
    }


    /**
     * 根据邮箱查询用户列表， 存在多个，是因为可能邮箱都未认证
     *
     * @param email
     * @return
     */
    public List<UserInfo> listUserByEmail(String email) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getEmail, email);
        return userInfoMapper.selectList(wrapper);
    }

    /**
     * 根据已认证的邮箱查询用户
     *
     * @param email
     * @return
     */
    public UserInfo getUserByVerifiedEmail(String email) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getEmail, email)
                .eq(UserInfo::getEmailVerified, true);
        return userInfoMapper.selectOne(wrapper);
    }

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    public boolean exitsByMobile(String mobile) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getMobile, mobile);
        return userInfoMapper.selectCount(wrapper) > 0;
    }


    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    public boolean exitsByEmail(String email) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getEmail, email);
        return userInfoMapper.selectCount(wrapper) > 0;
    }

    /**
     * 完善用户信息相关的更新
     *
     * @param command
     * @return
     */
    public int completeUserInfo(CompleteUserInfoCommand command) {
        final LambdaUpdateWrapper<UserInfo> wrapper = Wrappers.lambdaUpdate();
        if (Objects.nonNull(command.getEmail())) {
            wrapper.set(UserInfo::getEmail, "");
            wrapper.set(UserInfo::getTempEmail, command.getEmail());
        }
        if (Objects.nonNull(command.getNickname())) {
            wrapper.set(UserInfo::getNickname, command.getNickname());
        }
        if (Objects.nonNull(command.getAvatarUrl())) {
            wrapper.set(UserInfo::getAvatarUrl, command.getAvatarUrl());
        }
        if (Objects.nonNull(command.getAvatarThumbUrl())) {
            wrapper.set(UserInfo::getAvatarThumbUrl, command.getAvatarThumbUrl());
        }
        if (Objects.nonNull(command.getEmailVerified())) {
            wrapper.set(UserInfo::getEmailVerified, command.getEmailVerified());
        }
        wrapper.eq(UserInfo::getId, command.getId());
        return userInfoMapper.update(null, wrapper);
    }

    /**
     * 验证邮箱状态
     *
     * @param userId
     * @param email
     * @return
     */
    public int verifiedEmail(Long userId, String email) {
        final LambdaUpdateWrapper<UserInfo> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(UserInfo::getId, userId);
        wrapper.eq(UserInfo::getTempEmail, email);
        wrapper.eq(UserInfo::getEmailVerified, Boolean.FALSE);
        wrapper.set(UserInfo::getEmailVerified, Boolean.TRUE);
        wrapper.set(UserInfo::getEmail, email);
        return userInfoMapper.update(null, wrapper);
    }

    /**
     * 根据用户id集合查询用户对应的列表信息
     *
     * @param uidList
     * @return
     */
    public Map<Long, UserInfo> mapListUsers(Set<Long> uidList) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.in(UserInfo::getId, uidList);
        final List<UserInfo> users = userInfoMapper.selectList(wrapper);
        return users.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
    }
}
