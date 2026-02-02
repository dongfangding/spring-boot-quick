package com.ddf.boot.quickstart.core.infra.model.entity;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2025/12/19 11:48
 */


/**
 * 字典表
 */
public class SysDict {
    private static final long serialVersionUID = 1L;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    public String getDictDetailCode() {
        return dictDetailCode;
    }

    public void setDictDetailCode(String dictDetailCode) {
        this.dictDetailCode = dictDetailCode;
    }

    public String getDictDetailName() {
        return dictDetailName;
    }

    public void setDictDetailName(String dictDetailName) {
        this.dictDetailName = dictDetailName;
    }

    public String getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(String requestValue) {
        this.requestValue = requestValue;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
