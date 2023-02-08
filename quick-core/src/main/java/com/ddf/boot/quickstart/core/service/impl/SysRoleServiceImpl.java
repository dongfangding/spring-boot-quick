package com.ddf.boot.quickstart.core.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.common.api.model.common.PageResult;
import com.ddf.boot.common.core.model.BaseDomain;
import com.ddf.boot.common.core.util.PageUtil;
import com.ddf.boot.quickstart.api.request.sys.SysRolePageRequest;
import com.ddf.boot.quickstart.core.entity.SysRole;
import com.ddf.boot.quickstart.core.mapper.SysRoleMapper;
import com.ddf.boot.quickstart.core.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    /**
     * 新增记录
     *
     * @param sysRole
     * @return
     */
    @Override
    public boolean insert(SysRole sysRole) {
        return super.save(sysRole);
    }

    /**
     * 更新记录
     *
     * @param sysRole
     * @return
     */
    @Override
    public boolean update(SysRole sysRole) {
        return super.updateById(sysRole);
    }

    /**
     * 根据角色名字查询记录， 包含未激活的
     *
     * @param roleName
     * @return
     */
    @Override
    public SysRole getByName(String roleName) {
        final LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRole::getRoleName, roleName)
                .eq(SysRole::getIsDel, BaseDomain.IS_DEL_LOGIC_VALID_VALUE);
        return super.getOne(wrapper);
    }

    /**
     * 角色分页查询
     *
     * @param request
     * @return
     */
    @Override
    public PageResult<SysRole> pageList(SysRolePageRequest request) {
        final LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(request.getRoleName())) {
            wrapper.likeRight(SysRole::getRoleName, request.getRoleName());
        }
        wrapper.eq(SysRole::getIsDel, BaseDomain.IS_DEL_LOGIC_VALID_VALUE);
        wrapper.orderByDesc(SysRole::getCreateTime);
        return PageUtil.ofMybatis(super.page(PageUtil.toMybatis(request), wrapper));
    }
}
