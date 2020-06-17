package com.ddf.boot.quick.exception;

import com.ddf.boot.common.exception.GlobalCustomizeException;

/**
 * <p>请求参数无效异常</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/06/17 11:32
 */
public class BadRequestException extends GlobalCustomizeException {

    public BadRequestException(Throwable e) {
        super(e);
    }
}
