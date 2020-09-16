package com.ddf.boot.quick.websocket.properties;

import com.ddf.boot.quick.websocket.config.WebSocketConfig;
import com.ddf.boot.quick.websocket.helper.WebsocketSessionStorage;
import com.ddf.boot.quick.websocket.interceptor.DefaultHandshakeInterceptor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/09/15 20:11
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.websocket.properties")
public class WebSocketProperties {

    /**
     * 最大文本数据包大小
     * @see org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean
     */
    private Integer maxTextMessageBufferSize = 8192;

    /**
     * 最大二进制数据包大小
     * @see org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean
     */
    private Integer maxBinaryMessageBufferSize = 8192;

    /**
     * 最大session空闲时间，超过空闲时间会断开连接,单位毫秒
     * @see org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean
     */
    private Long maxSessionIdleTimeout = 60000L;

    /**
     * 暴露的服务端点
     * @see WebSocketConfig#registerWebSocketHandlers(org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry)
     */
    private String endPoint = "ddf-ws";

    /**
     * @see ConcurrentWebSocketSessionDecorator
     */
    private int sendTimeLimit = 3000;

    /**
     * @see ConcurrentWebSocketSessionDecorator
     */
    private int bufferSizeLimit = 102400;

    /**
     * 握手认证器， 系统提供一个默认的握手处理器的一个流程。如果不需要自定义实现，则默认的会生效
     * @see DefaultHandshakeInterceptor
     * @see WebSocketConfig#registerWebSocketHandlers(org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry)
     */
    private List<HandshakeInterceptor> handshakeInterceptors;

    /**
     * 认证参数中的毫秒值有效毫秒值， 当认证参数中携带的时间戳与当前时间已经间隔大于这个值，则视为参数无效
     * @see DefaultHandshakeInterceptor#validArgument(com.ddf.boot.quick.websocket.model.HandshakeParam, org.springframework.http.server.ServerHttpResponse)
     */
    private long validAuthTimeStamp = 60 * 1000 * 5;

    /**
     * 是否忽略验证参数的时间戳，方便调试时可以改为true
     * @see DefaultHandshakeInterceptor#validArgument(com.ddf.boot.quick.websocket.model.HandshakeParam, org.springframework.http.server.ServerHttpResponse)
     */
    private boolean ignoreAuthTimestamp;

    /**
     * 是否加密传输消息
     * @see WebsocketSessionStorage#sendMessage(com.ddf.boot.quick.websocket.model.WebSocketSessionWrapper, com.ddf.boot.quick.websocket.model.Message)
     */
    private boolean messageSecret;

    /**
     * 有时候服务端向客户端发送数据时会希望得到一些响应，而这个响应当前这个请求希望是可以同步得到的，这里提供一个默认最大阻塞时间.
     * 更为一种常见的场景是，服务端提供接口允许第三方发送数据给客户端， 然后索要数据，而这个时候第三方是希望同步的。
     * 当然具体请求可以自定义阻塞时间，但自定义的阻塞时间不能大于这个默认的最大阻塞时间，这是为了对应用的一个保护
     * @see com.ddf.boot.quick.websocket.model.MessageRequest
     * @see WebsocketSessionStorage#getResponse(java.lang.String, long)
     */
    private long maxSyncBlockMillions = 600000;
}
