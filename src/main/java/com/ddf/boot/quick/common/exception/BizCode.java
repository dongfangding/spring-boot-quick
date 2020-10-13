package com.ddf.boot.quick.common.exception;

import com.ddf.boot.common.core.exception200.BaseCallbackCode;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/10/13 16:59
 */
public enum BizCode implements BaseCallbackCode {
    /**
     * OSS异常
     */
    OSS_ERROR("10000", "OSS异常")

    ;


    BizCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;

    private String description;

    /**
     * 响应状态码
     *
     * @return
     */
    @Override
    public String getCode() {
        return null;
    }

    /**
     * 响应消息
     *
     * @return
     */
    @Override
    public String getDescription() {
        return null;
    }
}
