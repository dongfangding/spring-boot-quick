package com.ddf.boot.quick.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

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
public class SysDictDTO {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

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
