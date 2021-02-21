package com.ddf.boot.quick.model.request;

import com.ddf.boot.common.core.model.PageRequest;
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
public class PageUserHistoryBo implements PageRequest {

    /**
     * 页数, 从1开始
     */
    private Integer page;

    /**
     * 每页显示条数
     */
    private Integer pageSize;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;
}
