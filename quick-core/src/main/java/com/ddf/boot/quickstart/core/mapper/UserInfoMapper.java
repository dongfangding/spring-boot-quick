package com.ddf.boot.quickstart.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quickstart.core.entity.UserInfo;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/18 23:43
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    int deleteByPrimaryKey(Long id);

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
