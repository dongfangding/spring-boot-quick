package com.ddf.boot.quickstart.core.infra.mapper;

import com.ddf.boot.quickstart.core.infra.model.entity.UserInfo;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2025/12/19 11:48
 */
public interface UserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    /**
     * 随机n条用户
     *
     * @return
     */
    UserInfo randomUser(Integer num);
}