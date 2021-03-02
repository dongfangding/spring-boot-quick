package com.ddf.boot.quick.model.response;

import com.ddf.boot.quick.model.dto.SysUserDTO;
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
     * 用户基本信息
     *
     */
    private SysUserDTO baseInfo;


}
