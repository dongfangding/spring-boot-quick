package com.ddf.boot.quick.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户注册请求参数类
 *
 * @author dongfang.ding on 2019/12/8
 */
@Data
@ApiModel("用户注册请求参数")
@Accessors(chain = true)
public class AuthUserRegistryBo {

	@ApiModelProperty("姓名")
	private String userName;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("生日")
	private Date birthday;

}
