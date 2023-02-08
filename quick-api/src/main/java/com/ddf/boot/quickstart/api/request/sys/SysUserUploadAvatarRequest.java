package com.ddf.boot.quickstart.api.request.sys;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>用户头像上传</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/03 21:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserUploadAvatarRequest {

    /**
     * 头像相对地址url，不要域名前缀
     */
    @NotBlank(message = "头像地址不能为空")
    private String avatarUrl;
}
