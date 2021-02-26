package com.ddf.boot.quick.model.request;

import com.ddf.boot.common.core.model.PageRequest;
import javax.validation.constraints.Min;
import lombok.Data;

/**
 * <p>系统用户分页查询</p >
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
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum;

    /**
     * 每页显示条数
     */
    @Min(value = 1, message = "每页显示条数不能小于1")
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
