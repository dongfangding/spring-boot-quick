package com.ddf.boot.quick.websocket.interceptor;

import com.ddf.boot.quick.websocket.model.Message;

/**
 * 暴露一个对与websocket相关的传输的数据进行加密解密的实现$
 *
 * @author dongfang.ding
 * @date 2020/9/16 0016 22:37
 */
public interface EncryptProcessor {

    /**
     * 解密握手时需要的token参数
     * 如果客户端加密了的话
     * @param token
     * @return
     */
    default String decryptHandshakeToken(String token) {
        return token;
    }

    /**
     * 加密要发送的消息对象
     * @param message
     * @param <T>
     * @return
     */
    <T> String encryptMessage(Message<T> message);

}