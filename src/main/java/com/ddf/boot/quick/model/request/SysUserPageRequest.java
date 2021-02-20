package com.ddf.boot.quick.model.request;

import com.ddf.boot.common.core.model.PageRequest;
import lombok.Data;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 16:46
 */
@Data
public class SysUserPageRequest implements PageRequest {

    /**
     * 页数, 从1开始
     */
    private Integer page;

    /**
     * 每页显示条数
     */
    private Integer pageSize;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 昵称
     */
    private String nickname;

}
