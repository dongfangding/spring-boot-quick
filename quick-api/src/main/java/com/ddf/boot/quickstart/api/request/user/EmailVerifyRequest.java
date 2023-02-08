package com.ddf.boot.quickstart.api.request.user;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>邮箱验证参数类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/09/04 22:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerifyRequest implements Serializable {

    private static final long serialVersionUID = 1516322558409231083L;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
