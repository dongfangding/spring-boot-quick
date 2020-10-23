package com.ddf.boot.quick.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
	@NotEmpty(message = "姓名不能为空")
	private String username;

	@ApiModelProperty("密码")
	@NotEmpty(message = "密码不能为空")
	@Size(max = 18, min = 6, message = "密码必须位于6到18个字符之间")
	private String password;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("生日")
	private Date birthday;

}
