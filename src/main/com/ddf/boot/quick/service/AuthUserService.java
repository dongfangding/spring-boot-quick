package com.ddf.boot.quick.service;

import com.ddf.common.model.datao.quick.AuthUser;
import com.ddf.common.mybatis.service.CustomizeIService;

import javax.validation.constraints.NotNull;

/**
 * @author DDf on 2019/12/8
 */
public interface AuthUserService extends CustomizeIService<AuthUser> {

	/**
	 * 登录
	 *
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 */
	String login(String userName, String password);


	/**
	 * 根据用户名查找用户
	 *
	 * @param userName
	 * @return
	 */
	AuthUser findByName(String userName);


	/**
	 * 根据用户名和密码查找用户
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	AuthUser getByUserNameAndPassword(@NotNull String userName, @NotNull String password);
}
