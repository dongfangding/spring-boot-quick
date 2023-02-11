package com.ddf.boot.quickstart.api.request.sys;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>修改密码</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/08 18:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPasswordRequest implements Serializable {

    private static final long serialVersionUID = 4617849089338695076L;

    @NotBlank(message = "新密码不能为空")
    private String password;

}
