package com.ddf.boot.quickstart.api.request.sys;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>系统用户重置密码请求类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/23 22:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest implements Serializable {
    private static final long serialVersionUID = 4043147026743448255L;

    /**
     * 主键记录id
     */
    @NotNull(message = "id不能为空")
    private Long id;
}
