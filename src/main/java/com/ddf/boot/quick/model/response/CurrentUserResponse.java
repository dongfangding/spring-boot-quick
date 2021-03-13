package com.ddf.boot.quick.model.response;

import com.ddf.boot.quick.model.dto.SysUserDTO;
import com.ddf.boot.quick.model.dto.SysUserRoleDTO;
import java.util.Collections;
import java.util.List;
import lombok.Data;

/**
 * <p>当前用户信息</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 22:23
 */
@Data
public class CurrentUserResponse {

    /**
     * 是否是超级管理员
     */
    private boolean admin;

    /**
     * 角色集合
     */
    private List<SysUserRoleDTO> roles = Collections.emptyList();

    /**
     * 用户基本信息
     *
     */
    private SysUserDTO baseInfo;


}
