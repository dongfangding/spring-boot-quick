package com.ddf.boot.quick.model.request;

import com.ddf.boot.common.core.validator.constraint.Mobile;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p>发送手机验证码请求类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/15 23:05
 */
@Data
public class SendSmsCodeRequest implements Serializable {

    private static final long serialVersionUID = -8294321521799344603L;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Mobile
    private String mobile;

    /**
     * 验证码接口返回的tokenId
     */
    @NotBlank(message = "验证码随机数不能为空")
    private String tokenId;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
