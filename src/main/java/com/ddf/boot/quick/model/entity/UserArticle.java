package com.ddf.boot.quick.model.entity;

import com.ddf.boot.common.core.entity.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户文章$
 *
 * @author dongfang.ding
 * @date 2020/9/4 0004 23:34
 */
@Data
@ApiModel("用户文章")
public class UserArticle extends BaseDomain implements Serializable {

    static final long serialVersionUID = 42L;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章内容")
    private String content;


}
