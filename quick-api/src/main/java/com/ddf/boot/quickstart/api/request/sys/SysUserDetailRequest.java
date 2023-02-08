package com.ddf.boot.quickstart.api.request.sys;

import com.ddf.boot.common.api.model.common.request.BaseSign;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>用户详情查询参数</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/03 22:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDetailRequest implements BaseSign {

    @NotBlank(message = "用户id不能为空")
    private String userId;

    private String sign;

    private Long nonceTimestamp;
}
