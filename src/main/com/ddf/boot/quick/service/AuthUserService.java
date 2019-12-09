package com.ddf.boot.quick.service;

import com.ddf.boot.common.model.datao.quick.AuthUser;
import com.ddf.boot.common.mybatis.service.CustomizeIService;
import com.ddf.boot.quick.model.bo.AuthUserRegistryVo;
import com.ddf.boot.quick.model.vo.AuthUserVo;

import javax.validation.constraints.NotNull;

/**
 * @author DDf on 2019/12/8
 */
public interface AuthUserService extends CustomizeIService<AuthUser> {


	/**
	 * 用户注册
	 *
	 * @param authUserRegistryVo
	 * @return
	 */
	AuthUserVo registry(AuthUserRegistryVo authUserRegistryVo);


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
