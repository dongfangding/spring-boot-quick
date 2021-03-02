package com.ddf.boot.quick.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quick.model.entity.SysMenu;
import com.ddf.boot.quick.model.entity.SysRoleMenu;
import com.ddf.boot.quick.model.request.SysRoleMenuAuthorizationRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色与菜单关联表 Mapper 接口
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 批量给角色关联菜单
     *
     * @param request
     * @return
     */
    Integer batchInsert(@Param("request") SysRoleMenuAuthorizationRequest request);

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
}
