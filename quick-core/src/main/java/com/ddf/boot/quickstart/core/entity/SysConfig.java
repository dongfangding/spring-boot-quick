package com.ddf.boot.quickstart.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ddf.boot.common.core.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 配置表
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config" )
public class SysConfig extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 配置代码
     */
    private String configCode;

    /**
     * 配置json串
     */
    private String configValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否可修改 0 否 1 是
     */
    private Integer isEditable;


}
