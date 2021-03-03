package com.ddf.boot.quick.model.request;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * <p>用户详情查询参数</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/03 22:05
 */
@Data
public class SysUserDetailRequest {

    @NotEmpty(message = "用户id不能为空")
    private String userId;
}
