package com.ddf.boot.quick.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.common.core.exception200.BadRequestException;
import com.ddf.boot.common.core.exception200.BusinessException;
import com.ddf.boot.common.core.exception200.UserErrorCallbackCode;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.core.util.IdsUtil;
import com.ddf.boot.common.core.util.SecureUtil;
import com.ddf.boot.common.core.util.WebUtil;
import com.ddf.boot.common.ids.helper.SnowflakeServiceHelper;
import com.ddf.boot.common.jwt.model.UserClaim;
import com.ddf.boot.common.jwt.util.JwtUtil;
import com.ddf.boot.common.model.datao.quick.AuthUser;
import com.ddf.boot.common.mybatis.service.impl.CusomizeIServiceImpl;
import com.ddf.boot.quick.biz.AsyncTask;
import com.ddf.boot.quick.mapper.AuthUserMapper;
import com.ddf.boot.quick.model.bo.AuthUserPageBo;
import com.ddf.boot.quick.model.bo.AuthUserRegistryBo;
import com.ddf.boot.quick.model.bo.LoginBo;
import com.ddf.boot.quick.model.vo.AuthUserVo;
import com.ddf.boot.quick.mongo.collection.UserLoginHistoryCollection;
import com.ddf.boot.quick.oss.BootOssClient;
import com.ddf.boot.quick.service.AuthUserService;
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
	@Autowired
	private SnowflakeServiceHelper snowflakeServiceHelper;
	@Autowired
	private BootOssClient bootOssClient;

	/**
	 * 用户注册
	 *
	 * @param authUserRegistryBo
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AuthUserVo registry(AuthUserRegistryBo authUserRegistryBo) {
		LambdaQueryWrapper<AuthUser> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AuthUser::getUsername, authUserRegistryBo.getUsername());
		if (count(queryWrapper) > 0) {
			throw new BadRequestException("用户已存在!");
		}

		AuthUser authUser = new AuthUser();
		BeanUtil.copyProperties(authUserRegistryBo, authUser);
		// 随机给用户生成一个盐（当然如果用户主键是提前生成的，也可以使用主键）
		String userToken = IdsUtil.getNextStrId();
		authUser.setId(snowflakeServiceHelper.getLongId());
		authUser.setUserToken(userToken);
		authUser.setAvatar(bootOssClient.uploadAvatar(authUser.getUsername()));
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
		String username = loginBo.getUsername();
		String password = loginBo.getPassword();

		AuthUser existUser = findByName(username);
		if (existUser == null) {
			throw new BusinessException(UserErrorCallbackCode.USER_NOT_EXIST);
		}

		String userToken = existUser.getUserToken();

		LambdaQueryWrapper<AuthUser> userQueryWrapper = Wrappers.lambdaQuery();
		userQueryWrapper.eq(AuthUser::getUsername, username);
		userQueryWrapper.eq(AuthUser::getPassword, SecureUtil.signWithHMac(password, userToken));
		if (count(userQueryWrapper) != 1) {
			throw new BusinessException(UserErrorCallbackCode.PASSWORD_ERROR);
		}

		long loginTime = System.currentTimeMillis();

		UserClaim userClaim = new UserClaim();
		userClaim.setUserId(Convert.toStr(existUser.getId()))
				.setUsername(existUser.getUsername())
				.setLastModifyPasswordTime(existUser.getLastModifyPassword())
				.setCredit(WebUtil.getHost())
				// 记录用户当前登录时间
				.setLastLoginTime(loginTime);
		String verifyToken = JwtUtil.defaultJws(userClaim);

		// 更新用户最后一次登录时间
		existUser.setLastLoginTime(loginTime);
		updateById(existUser);

		// 登录日志
		asyncTask.logUserLoginHistory(new UserLoginHistoryCollection()
				.setUserId(existUser.getId())
				.setUsername(existUser.getUsername())
				.setToken(verifyToken)
				.setLoginTime(new Date(loginTime))
				.setLoginIp(WebUtil.getHost())
				.setLoginAddress(WebUtil.getCurRequest().getLocale().getDisplayCountry())
		);
		return verifyToken;
	}

	/**
	 * 根据用户名查找用户
	 *
	 * @param username
	 * @return
	 */
	@Override
	public AuthUser findByName(String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		LambdaQueryWrapper<AuthUser> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AuthUser::getUsername, username);
		return getOne(queryWrapper);
	}

	/**
	 * 分页查询
	 *
	 * @param authUserPageBo
	 * @return
	 */
	@Override
	public PageResult<AuthUser> pageList(AuthUserPageBo authUserPageBo) {
		LambdaQueryWrapper<AuthUser> userQueryWrapper = Wrappers.lambdaQuery();
		if (StringUtils.isNotBlank(authUserPageBo.getUsername())) {
			userQueryWrapper.like(AuthUser::getUsername, authUserPageBo.getUsername());
		}
		if (StringUtils.isNotBlank(authUserPageBo.getEmail())) {
			userQueryWrapper.like(AuthUser::getEmail, authUserPageBo.getEmail());
		}
		return PageResult.ofMybatis(page(authUserPageBo.toMybatis(), userQueryWrapper));
	}


}
