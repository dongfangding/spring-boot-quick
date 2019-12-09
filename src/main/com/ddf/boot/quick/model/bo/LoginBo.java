package com.ddf.boot.quick.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    private String userName;

    @ApiModelProperty("密码")
    private String password;
}
