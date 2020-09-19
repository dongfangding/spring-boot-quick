package com.ddf.boot.quick.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户展示层对象
 *
 * @author dongfang.ding
 * @date 2019/10/9 11:47
 */
@Data
@ApiModel("用户展示层对象")
@Accessors(chain = true)
public class AuthUserVo {

    @ApiModelProperty("用户id")
    private String id;

	@ApiModelProperty("姓名")
	private String username;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("生日")
	private Date birthday;

	@ApiModelProperty("最后一次修改密码的时间")
	private Long lastModifyPassword;

	@ApiModelProperty("上次登录密码的时间")
	private Long lastLoginTime;
}
