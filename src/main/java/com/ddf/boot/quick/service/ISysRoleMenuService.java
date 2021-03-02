package com.ddf.boot.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.quick.model.entity.SysMenu;
import com.ddf.boot.quick.model.entity.SysRoleMenu;
import com.ddf.boot.quick.model.request.SysRoleMenuAuthorizationRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色与菜单关联表 服务类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据id获取记录
     *
     * @param id
     * @return
     */
    default SysRoleMenu getByPrimaryKey(Long id) {
        return getById(id);
    }

    /**
     * 删除角色下已有菜单
     *
     * @param roleId
     * @return
     */
    boolean deleteByRoleId(Long roleId);

    /**
     * 新增记录
     *
     * @param sysRoleMenu
     * @return
     */
    boolean insert(SysRoleMenu sysRoleMenu);


    /**
     * 更新记录
     * @param sysRoleMenu
     * @return
     */
    boolean update(SysRoleMenu sysRoleMenu);


    /**
     * 获取用户已分配的菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> getUserActiveMenu(@Param("userId") String userId);

    /**
     * 查询某个角色下已授权菜单id集合
     *
     * @param roleId
     * @return
     */
    List<Long> getRoleActiveMenuIds(@Param("roleId") Long roleId);

    /**
     * 批量给角色关联菜单
     *
     * @param request
     * @return
     */
    Integer batchInsert(SysRoleMenuAuthorizationRequest request);
}
