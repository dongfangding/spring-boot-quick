package com.ddf.boot.quickstart.core.mapper;

import com.ddf.boot.quickstart.core.entity.UserInfo;
import com.ddf.boot.quickstart.core.model.cqrs.user.CompleteUserInfoCommand;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/18 23:43
 */
public interface UserInfoMapper {
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

    List<UserInfo> listByIds(@Param("userIds") List<Long> userIds);

    UserInfo selectByMobile(@Param("mobile") String mobile);

    UserInfo selectByNickname(@Param("nickname") String nickname);

    int countByNickname(@Param("nickname") String nickname);

    UserInfo selectByEmail(@Param("email") String email);

    int countByMobile(@Param("mobile") String mobile);

    int completeUserInfo(CompleteUserInfoCommand command);

    int updateVerifyEmail(@Param("id") Long id, @Param("email") String email);
}
