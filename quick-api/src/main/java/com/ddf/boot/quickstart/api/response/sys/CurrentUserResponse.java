package com.ddf.boot.quickstart.api.response.sys;

import com.ddf.boot.quickstart.api.dto.SysUserDTO;
import com.ddf.boot.quickstart.api.dto.SysUserRoleDTO;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>当前用户信息</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 22:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserResponse implements Serializable {

    private static final long serialVersionUID = -1756414882990695540L;

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
