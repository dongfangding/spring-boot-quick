package com.ddf.boot.quick.model.request;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * <p>用户头像上传</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/03 21:48
 */
@Data
public class SysUserUploadAvatarRequest {

    /**
     * 头像相对地址url，不要域名前缀
     */
    @NotEmpty(message = "头像地址不能为空")
    private String avatarUrl;
}
