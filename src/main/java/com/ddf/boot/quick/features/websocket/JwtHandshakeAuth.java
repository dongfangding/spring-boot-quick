package com.ddf.boot.quick.features.websocket;

import com.ddf.boot.common.websocket.interceptor.DefaultHandshakeInterceptor;
import com.ddf.boot.common.websocket.interceptor.HandshakeAuth;
import com.ddf.boot.common.websocket.model.AuthPrincipal;
import com.ddf.boot.common.websocket.model.HandshakeParam;
import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

/**
 * <p>基于jwt的参数认证</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/09/16 12:19
 */
@Component
public class JwtHandshakeAuth implements HandshakeAuth {
    /**
     * 基于默认的握手实现，校验认证参数，返回认证身份，可以提供多个实现
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @param handshakeParam
     * @return
     * @see DefaultHandshakeInterceptor
     */
    @Override
    public AuthPrincipal validPrincipal(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes, HandshakeParam handshakeParam) {
        return new AuthPrincipal(handshakeParam.getAccessKeyId(), handshakeParam.getAccessKeyName(),
                handshakeParam.getAuthCode(), handshakeParam.getLoginType(), handshakeParam.getVersion(),
                handshakeParam.getCurrentTimeStamp()
        );
    }
}
