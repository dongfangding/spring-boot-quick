package com.ddf.boot.quickstart.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quickstart.api.dto.SysUserRoleDTO;
import com.ddf.boot.quickstart.api.request.sys.SysUserRoleBatchInsertRequest;
import com.ddf.boot.quickstart.core.entity.SysUserRole;
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
    int batchRelativeUser(@Param("request") SysUserRoleBatchInsertRequest request);

    /**
     * 获取用户有效的角色列表
     *
     * @param userId
     * @return
     */
    List<SysUserRoleDTO> getUserActiveRoleList(@Param("userId") String userId);
}
