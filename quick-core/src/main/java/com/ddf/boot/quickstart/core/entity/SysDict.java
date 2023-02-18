package com.ddf.boot.quickstart.core.entity;

import java.io.Serializable;
import lombok.Data;

/**
* <p>字典表</p >
*
* @author Snowball
* @version 1.0
* @date 2023/02/18 23:43
*/
@Data
public class SysDict implements Serializable {
    private Long id;

    /**
     * 字典类型
     */
    private String dictTypeCode;

    /**
     * 字典名称
     */
    private String dictTypeName;

    /**
     * 字典明细代码
     */
    private String dictDetailCode;

    /**
     * 字段明细名称
     */
    private String dictDetailName;

    /**
     * 请求参数，比如这个字段后台映射为枚举的时候，那么字典是用来渲染的，但是请求的时候却是要用对应属性的枚举名，就是这个字段
     */
    private String requestValue;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否有效
     */
    private String active;

    private static final long serialVersionUID = 1L;
}
