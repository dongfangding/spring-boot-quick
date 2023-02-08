package com.ddf.boot.quickstart.api.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDictDTO implements Serializable {

    private static final long serialVersionUID = -8520994509280038539L;

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
