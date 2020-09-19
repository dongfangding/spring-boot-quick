package com.ddf.boot.quick.model.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录日志分页查询参数$
 *
 * @author dongfang.ding
 * @date 2020/9/19 0019 14:47
 */
@Data
@ApiModel("用户登录日志分页查询参数$")
public class PageUserHistoryBo {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;
}
