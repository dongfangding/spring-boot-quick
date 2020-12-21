package com.ddf.boot.quick.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录请求参数$
 *
 * @author dongfang.ding
 * @date 2019/12/9 0009 20:03
 */
@Data
@ApiModel("登录请求参数")
@Accessors(chain = true)
public class LoginBo {

    @ApiModelProperty("用户名")
    @NotEmpty(message = "姓名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;
}
