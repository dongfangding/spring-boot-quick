package com.ddf.boot.quick.model.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>系统用户重置密码请求类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/23 22:03
 */
@Data
public class ResetPasswordRequest {

    /**
     * 主键记录id
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
