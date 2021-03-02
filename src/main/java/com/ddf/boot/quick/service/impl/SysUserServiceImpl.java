package com.ddf.boot.quick.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.common.core.enumration.CommonLogic;
import com.ddf.boot.common.core.model.BaseDomain;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.core.util.PageUtil;
import com.ddf.boot.quick.mapper.SysUserMapper;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.model.request.SysUserPageRequest;
import com.ddf.boot.quick.service.ISysUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    /**
     * 根据主键删除记录
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteByPrimaryKey(Long id) {
        final LambdaUpdateWrapper<SysUser> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(SysUser::getIsDel, BaseDomain.IS_DEL_LOGIC_DELETE_VALUE);
        wrapper.eq(SysUser::getId, id);
        return false;
    }

    /**
     * 根据userId获取记录
     *
     * @param userId
     * @return
     */
    @Override
    public SysUser getByUserId(String userId) {
        final LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUserId, userId)
                .eq(SysUser::getIsDel, CommonLogic.FALSE.getLogic());
        return super.getOne(wrapper);
    }

    /**
     * 根据userId集合获取用户信息
     *
     * @param userIds
     * @return
     */
    @Override
    public List<SysUser> getByUserIds(List<String> userIds) {
        final LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.in(SysUser::getUserId, userIds)
                .eq(SysUser::getIsDel, CommonLogic.FALSE.getLogic());
        return super.list(wrapper);
    }

    /**
     * 新增记录
     *
     * @param sysUser
     * @return
     */
    @Override
    public boolean insert(SysUser sysUser) {
        return super.save(sysUser);
    }

    /**
     * 更新记录
     *
     * @param sysUser
     * @return
     */
    @Override
    public boolean update(SysUser sysUser) {
        return super.updateById(sysUser);
    }

    /**
     * 根据登录账号和密码获取用户信息
     *
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public SysUser getByLoginNameAndPassword(String loginName, String password) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getLoginName, loginName)
                .eq(SysUser::getPassword, password)
                .eq(SysUser::getIsDel, CommonLogic.FALSE.getLogic());
        return super.getOne(wrapper);
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return
     */
    @Override
    public SysUser getByLoginName(String loginName) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getLoginName, loginName)
                .eq(SysUser::getIsDel, CommonLogic.FALSE.getLogic());
        return super.getOne(wrapper);
    }

    /**
     * 根据手机号获取用户
     *
     * @param mobile
     * @return
     */
    @Override
    public SysUser getByMobile(String mobile) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getMobile, mobile)
                .eq(SysUser::getIsDel, CommonLogic.FALSE.getLogic());
        return super.getOne(wrapper);
    }

    /**
     * 根据昵称获取用户
     *
     * @param nickname
     * @return
     */
    @Override
    public SysUser getByNickname(String nickname) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getNickname, nickname)
                .eq(SysUser::getIsDel, CommonLogic.FALSE.getLogic());
        return super.getOne(wrapper);
    }

    /**
     * 系统用户分页查询
     *
     * @param request
     * @return
     */
    @Override
    public PageResult<SysUser> pageList(SysUserPageRequest request) {
        final LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(request.getLoginName())) {
            wrapper.likeRight(SysUser::getLoginName, request.getLoginName());
        }
        if (StrUtil.isNotBlank(request.getNickname())) {
            wrapper.likeRight(SysUser::getNickname, request.getNickname());
        }
        wrapper.eq(SysUser::getIsDel, CommonLogic.FALSE.getLogic());
        wrapper.orderByDesc(SysUser::getCreateTime);
        return PageUtil.ofMybatis(super.page(PageUtil.toMybatis(request), wrapper));
    }
}
