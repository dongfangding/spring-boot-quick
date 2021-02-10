package com.ddf.boot.quick.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quick.model.bo.BatchInsertSysUserRoleRequest;
import com.ddf.boot.quick.model.entity.SysUserRole;
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
}
