package com.ddf.boot.quick.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quick.model.dto.SysUserRoleDTO;
import com.ddf.boot.quick.model.entity.SysUserRole;
import com.ddf.boot.quick.model.request.BatchInsertSysUserRoleRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户-角色关联表 Mapper 接口
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 批量关联用户角色
     *
     * @param request
     * @return
     */
    int batchRelativeUser(@Param("request") BatchInsertSysUserRoleRequest request);

    /**
     * 获取用户有效的角色列表
     *
     * @param userId
     * @return
     */
    List<SysUserRoleDTO> getUserActiveRoleList(@Param("userId") String userId);
}
