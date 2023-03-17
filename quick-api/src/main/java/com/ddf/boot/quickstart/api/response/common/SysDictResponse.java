package com.ddf.boot.quickstart.api.response.common;

import java.io.Serializable;
import lombok.Data;


/**
 * 字典表
 *
 * @author snowball
 * @date 2022/8/29 16:57
 **/
@Data
public class SysDictResponse implements Serializable {

    private static final long serialVersionUID = -2231204865227593502L;

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

}
