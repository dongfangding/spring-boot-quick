package com.ddf.boot.quick.websocket.handler;


import com.ddf.boot.quick.websocket.helper.WebsocketSessionStorage;
import com.ddf.boot.quick.websocket.model.AuthPrincipal;
import com.ddf.boot.quick.websocket.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 *
 * 自定义实现消息处理器以及事件的监听
 *
 * @author dongfang.ding
 * @date 2019/8/20 11:43
 */
@Slf4j
public class CustomizeWebSocketHandler extends AbstractWebSocketHandler {

    private final HandlerMessageService handlerMessageService;

    public CustomizeWebSocketHandler(HandlerMessageService handlerMessageService) {
        this.handlerMessageService = handlerMessageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        AuthPrincipal principal = (AuthPrincipal) session.getPrincipal();
        log.info("[{}-{}-{}]建立连接成功.....", principal.getLoginType(), principal.getAccessKeyId(), principal.getAuthCode());
        WebsocketSessionStorage.active((AuthPrincipal) session.getPrincipal(), session);
        WebsocketSessionStorage.sendMessage((AuthPrincipal) session.getPrincipal(), Message.echo("现在开始可以和服务器通讯了"));
    }

    /**
     * 待测试，这个方法好像是同步的
     * @param session
     * @param textMessage
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        log.info("-----------------handleTextMessage------------------");
        AuthPrincipal principal = (AuthPrincipal) session.getPrincipal();
        if (principal == null || StringUtils.isAnyBlank(principal.getAccessKeyId(), principal.getAuthCode())) {
            session.sendMessage(Message.wrapper(Message.responseNotLogin(textMessage)));
            session.close();
            return;
        }
        log.info("[{}-{}-{}]收到消息: {}]", principal.getLoginType(), principal.getAccessKeyId(), principal.getAuthCode(), textMessage.getPayload());
        if (handlerMessageService != null) {
            handlerMessageService.handlerMessage(principal, WebsocketSessionStorage.get(principal), textMessage);
        }
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        log.info("-----------------handlePongMessage------------------");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        AuthPrincipal principal = (AuthPrincipal) session.getPrincipal();
        log.info("[{}-{}-{}]handleTransportError.....", principal.getLoginType(), principal.getAccessKeyId(),
                principal.getAuthCode(), exception);
        WebsocketSessionStorage.inactive(principal, session);
        session.close();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        AuthPrincipal principal = (AuthPrincipal) session.getPrincipal();
        log.info("[{}-{}-{}]afterConnectionClosed.....CloseStatus: {}", principal.getLoginType(),
                principal.getAccessKeyId(), principal.getAuthCode(), status);
        WebsocketSessionStorage.inactive((AuthPrincipal) session.getPrincipal(), session);
    }
}
