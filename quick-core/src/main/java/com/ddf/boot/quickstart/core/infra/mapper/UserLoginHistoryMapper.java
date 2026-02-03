package com.ddf.boot.quickstart.core.infra.mapper;

import com.ddf.boot.quickstart.core.infra.model.entity.UserLoginHistory;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2025/12/19 11:48
 */
public interface UserLoginHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserLoginHistory record);

    int insertSelective(UserLoginHistory record);

    UserLoginHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLoginHistory record);

    int updateByPrimaryKey(UserLoginHistory record);
}