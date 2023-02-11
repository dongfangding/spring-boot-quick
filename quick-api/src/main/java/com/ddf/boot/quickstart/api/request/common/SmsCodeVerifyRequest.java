package com.ddf.boot.quickstart.api.request.common;

import com.ddf.boot.common.api.validator.constraint.Mobile;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>短信验证码验证参数</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/22 11:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsCodeVerifyRequest implements Serializable {

    private static final long serialVersionUID = -8846320147721003035L;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Mobile
    private String mobile;

    /**
     * 表单id， 回传这个服务端来验证验证码
     */
    @NotBlank(message = "手机验证码uuid不能为空")
    private String uuid;

    /**
     * 手机验证码
     */
    @NotBlank(message = "手机验证码不能为空")
    private String mobileCode;
}
