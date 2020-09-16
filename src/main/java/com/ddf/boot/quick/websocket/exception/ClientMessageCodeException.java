package com.ddf.boot.quick.websocket.exception;

import com.ddf.boot.common.core.exception.GlobalCustomizeException;

/**
 * 客户端报文数据响应非200状态码异常
 *
 * @author dongfang.ding
 * @date 2019/9/20 16:11
 */
public class ClientMessageCodeException extends GlobalCustomizeException {

    public ClientMessageCodeException(String message) {
        super(message);
    }
}