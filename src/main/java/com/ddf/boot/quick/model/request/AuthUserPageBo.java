package com.ddf.boot.quick.model.request;

import com.ddf.boot.common.core.model.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户表分页查询参数$
 *
 * @author dongfang.ding
 * @date 2019/12/9 0009 21:33
 */
@Data
@ApiModel("用户分页查询")
public class AuthUserPageBo extends BaseQuery {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("邮箱")
    private String email;
}
