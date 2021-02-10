package com.ddf.boot.quick.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ddf.boot.common.core.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict" )
public class SysDict extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 字典代码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典明细代码
     */
    private String dictItemCode;

    /**
     * 字典明细名称
     */
    private String dictItemName;

    /**
     * 排序
     */
    private Integer sort;


}