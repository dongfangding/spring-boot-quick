package com.ddf.boot.quick.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.common.exception.GlobalCustomizeException;
import com.ddf.boot.common.exception.GlobalExceptionEnum;
import com.ddf.boot.common.jwt.model.UserClaim;
import com.ddf.boot.common.jwt.util.JwtUtil;
import com.ddf.boot.common.model.datao.quick.AuthUser;
import com.ddf.boot.common.mybatis.service.impl.CusomizeIServiceImpl;
import com.ddf.boot.common.util.IdsUtil;
import com.ddf.boot.common.util.SecureUtil;
import com.ddf.boot.common.util.WebUtil;
import com.ddf.boot.quick.mapper.AuthUserMapper;
import com.ddf.boot.quick.model.bo.AuthUserRegistryVo;
import com.ddf.boot.quick.model.vo.AuthUserVo;
import com.ddf.boot.quick.service.AuthUserService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * @author DDf on 2019/12/8
 */
@Service
public class AuthUserServiceImpl extends CusomizeIServiceImpl<AuthUserMapper, AuthUser> implements AuthUserService {


	@Autowired
	private IdsUtil idsUtil;

	/**
	 * 用户注册
	 *
	 * @param authUserRegistryVo
	 * @return
	 */
	@Override
	public AuthUserVo registry(AuthUserRegistryVo authUserRegistryVo) {
		Preconditions.checkNotNull(authUserRegistryVo, "请求参数不能为空!");
		Preconditions.checkArgument(StringUtils.isNotBlank(authUserRegistryVo.getUserName()), "用户名不能为空！");
		Preconditions.checkArgument(StringUtils.isNotBlank(authUserRegistryVo.getPassword()), "密码不能为空！");

		LambdaQueryWrapper<AuthUser> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AuthUser::getUserName, authUserRegistryVo.getUserName());
		if (count(queryWrapper) > 0) {
			throw new GlobalCustomizeException(GlobalExceptionEnum.USERNAME_EXIST);
		}

		AuthUser authUser = new AuthUser();
		BeanUtil.copyProperties(authUserRegistryVo, authUser);
		String userToken = idsUtil.getNextStrId();
		authUser.setUserToken(userToken);
		authUser.setPassword(SecureUtil.signWithHMac(authUserRegistryVo.getPassword(), userToken));
		saveCheckDuplicateKey(authUser, new GlobalCustomizeException("用户已存在！"));
		AuthUserVo authUserVo = new AuthUserVo();
		BeanUtil.copyProperties(authUser, authUserVo);
		return authUserVo;
	}

	/**
	 * 登录
	 *
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public String login(@NotNull String userName, @NotNull String password) {
		Preconditions.checkArgument(StringUtils.isNotBlank(userName), "用户名不能为空!");
		Preconditions.checkArgument(StringUtils.isNotBlank(password), "密码不能为空!");
		AuthUser authUser = getByUserNameAndPassword(userName, password);
		if (authUser == null) {
			throw new GlobalCustomizeException(GlobalExceptionEnum.USERNAME_OR_PASSWORD_INVALID);
		}

		UserClaim userClaim = new UserClaim();
		userClaim.setUserId(Convert.toStr(authUser.getId())).setUsername(authUser.getUserName()).setLastModifyPasswordTime(
				// 默认注册时间
				authUser.getLastModifyPassword()).setCredit(WebUtil.getHost());
		return JwtUtil.defaultJws(userClaim);
	}

	/**
	 * 根据用户名查找用户
	 *
	 * @param userName
	 * @return
	 */
	@Override
	public AuthUser findByName(String userName) {
		if (StringUtils.isBlank(userName)) {
			return null;
		}
		LambdaQueryWrapper<AuthUser> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AuthUser::getUserName, userName);
		return getOne(queryWrapper);
	}

	/**
	 * 根据用户名和密码查找用户
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	@Override
	public AuthUser getByUserNameAndPassword(@NotNull String userName, @NotNull String password) {
		Preconditions.checkArgument(StringUtils.isNotBlank(userName), "用户名不能为空!");
		Preconditions.checkArgument(StringUtils.isNotBlank(password), "密码不能为空!");
		LambdaQueryWrapper<AuthUser> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AuthUser::getUserName, userName);
		queryWrapper.eq(AuthUser::getPassword, SecureUtil.signWithHMac(password));
		return getOne(queryWrapper);
	}
}
