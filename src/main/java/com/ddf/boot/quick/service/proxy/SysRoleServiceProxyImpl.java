package com.ddf.boot.quick.service.proxy;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.quick.mapper.SysRoleMapper;
import com.ddf.boot.quick.model.entity.SysRole;
import com.ddf.boot.quick.model.request.SysRolePageRequest;
import com.ddf.boot.quick.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>service实现的代理层， 这一层的目的是， 由于使用了mybatis-plus， 所以省略了dao层， 将service层完全只关注查询， 但是期望将来能够在查询上做缓存
 * 处理， 那么有些方法， 比如getById，这些方法本身service就存在， 想要在这个方法上加逻辑，就得重新再写一个方法然后调用父类的方法， 这样显的很不清晰， 所以
 * 直接抽出来一个代理层， 在代理层上做缓存或者别的事情， 但是这个代理层本身也会存在要注意的问题，由于真的使用了静态代理， 在注入的时候要小心同一个接口
 * 有两个实现的问题， 这里将代理层作为默认注入</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/23 10:39
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
@Primary
public class SysRoleServiceProxyImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final ISysRoleService sysRoleServiceImpl;

    /**
     * 根据主键获取记录
     *
     * @param id
     * @return
     */
    @Override
    public SysRole getByPrimaryKey(Long id) {
        return sysRoleServiceImpl.getByPrimaryKey(id);
    }

    /**
     * 新增记录
     *
     * @param sysRole
     * @return
     */
    @Override
    public boolean insert(SysRole sysRole) {
        return sysRoleServiceImpl.insert(sysRole);
    }

    /**
     * 更新记录
     *
     * @param sysRole
     * @return
     */
    @Override
    public boolean update(SysRole sysRole) {
        return sysRoleServiceImpl.update(sysRole);
    }

    /**
     * 根据角色名字查询记录， 包含未激活的
     *
     * @param roleName
     * @return
     */
    @Override
    public SysRole getByName(String roleName) {
        return sysRoleServiceImpl.getByName(roleName);
    }

    /**
     * 角色分页查询
     *
     * @param request
     * @return
     */
    @Override
    public PageResult<SysRole> pageList(SysRolePageRequest request) {
        return sysRoleServiceImpl.pageList(request);
    }
}
