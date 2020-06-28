package com.ddf.boot.quick.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddf.boot.common.exception200.BadRequestException;
import com.ddf.boot.common.exception200.BusinessException;
import com.ddf.boot.common.exception200.UserErrorCallbackCode;
import com.ddf.boot.common.jwt.model.UserClaim;
import com.ddf.boot.common.jwt.util.JwtUtil;
import com.ddf.boot.common.model.datao.quick.AuthUser;
import com.ddf.boot.common.mybatis.service.impl.CusomizeIServiceImpl;
import com.ddf.boot.common.util.IdsUtil;
import com.ddf.boot.common.util.SecureUtil;
import com.ddf.boot.common.util.WebUtil;
import com.ddf.boot.quick.biz.AsyncTask;
import com.ddf.boot.quick.mapper.AuthUserMapper;
import com.ddf.boot.quick.model.bo.AuthUserPageBo;
import com.ddf.boot.quick.model.bo.AuthUserRegistryBo;
import com.ddf.boot.quick.model.bo.LoginBo;
import com.ddf.boot.quick.model.dto.LogUserLoginHistoryDto;
import com.ddf.boot.quick.model.vo.AuthUserVo;
import com.ddf.boot.quick.service.AuthUserService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author DDf on 2019/12/8
 */
@Service
public class AuthUserServiceImpl extends CusomizeIServiceImpl<AuthUserMapper, AuthUser> implements AuthUserService {

	@Autowired
	private AsyncTask asyncTask;

	/**
	 * 用户注册
	 *
	 * @param authUserRegistryBo
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AuthUserVo registry(AuthUserRegistryBo authUserRegistryBo) {
		Preconditions.checkNotNull(authUserRegistryBo, "请求参数不能为空!");
		Preconditions.checkArgument(StringUtils.isNotBlank(authUserRegistryBo.getUserName()), "用户名不能为空！");
		Preconditions.checkArgument(StringUtils.isNotBlank(authUserRegistryBo.getPassword()), "密码不能为空！");

		LambdaQueryWrapper<AuthUser> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AuthUser::getUserName, authUserRegistryBo.getUserName());
		if (count(queryWrapper) > 0) {
			throw new BadRequestException("用户已存在!");
		}

		AuthUser authUser = new AuthUser();
		BeanUtil.copyProperties(authUserRegistryBo, authUser);
		// 随机给用户生成一个盐（当然如果用户主键是提前生成的，也可以使用主键）
		String userToken = IdsUtil.getNextStrId();
		authUser.setUserToken(userToken);
		authUser.setPassword(SecureUtil.signWithHMac(authUserRegistryBo.getPassword(), userToken));
		saveCheckDuplicateKey(authUser, new BadRequestException("用户已存在！"));
		AuthUserVo authUserVo = new AuthUserVo();
		BeanUtil.copyProperties(authUser, authUserVo);
		return authUserVo;
	}

	/**
	 * 登录
	 *
	 * @param loginBo
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String loginByPassword(@NotNull LoginBo loginBo) {
		Preconditions.checkNotNull(loginBo, "登录参数不能为空！");
		String userName = loginBo.getUserName();
		String password = loginBo.getPassword();
		Preconditions.checkArgument(StringUtils.isNotBlank(userName), "用户名不能为空!");
		Preconditions.checkArgument(StringUtils.isNotBlank(password), "密码不能为空!");

		AuthUser existUser = findByName(userName);
		if (existUser == null) {
			throw new BusinessException(UserErrorCallbackCode.USER_NOT_EXIST);
		}

		String userToken = existUser.getUserToken();

		LambdaQueryWrapper<AuthUser> userQueryWrapper = Wrappers.lambdaQuery();
		userQueryWrapper.eq(AuthUser::getUserName, userName);
		userQueryWrapper.eq(AuthUser::getPassword, SecureUtil.signWithHMac(password, userToken));
		if (count(userQueryWrapper) != 1) {
			throw new BusinessException(UserErrorCallbackCode.PASSWORD_ERROR);
		}

		long loginTime = System.currentTimeMillis();

		UserClaim userClaim = new UserClaim();
		userClaim.setUserId(Convert.toStr(existUser.getId()))
				.setUsername(existUser.getUserName())
				.setLastModifyPasswordTime(existUser.getLastModifyPassword())
				.setCredit(WebUtil.getHost())
				// 记录用户当前登录时间
				.setLastLoginTime(loginTime);
		String verifyToken = JwtUtil.defaultJws(userClaim);

		// 更新用户最后一次登录时间
		existUser.setLastLoginTime(loginTime);
		updateById(existUser);

		asyncTask.logUserLoginHistory(new LogUserLoginHistoryDto()
				.setUserId(existUser.getId())
				.setToken(verifyToken)
				.setLoginTime(new Date(loginTime))
				.setLoginIp(WebUtil.getHost())
		);

		return verifyToken;
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
	 * 分页查询
	 *
	 * @param page
	 * @param authUserPageBo
	 * @return
	 */
	@Override
	public IPage<AuthUser> pageList(Page<AuthUser> page, AuthUserPageBo authUserPageBo) {
		LambdaQueryWrapper<AuthUser> userQueryWrapper = Wrappers.lambdaQuery();
		if (StringUtils.isNotBlank(authUserPageBo.getUserName())) {
			userQueryWrapper.like(AuthUser::getUserName, authUserPageBo.getUserName());
		}
		if (StringUtils.isNotBlank(authUserPageBo.getEmail())) {
			userQueryWrapper.like(AuthUser::getEmail, authUserPageBo.getEmail());
		}
		return page(page, userQueryWrapper);
	}
}
