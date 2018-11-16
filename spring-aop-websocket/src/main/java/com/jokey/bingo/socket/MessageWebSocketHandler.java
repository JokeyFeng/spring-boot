package com.jokey.bingo.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author jokey
 */
@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(MessageWebSocketHandler.class);
    private static ConcurrentHashMap<String, CopyOnWriteArraySet<WebSocketSession>> users = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = session.getAttributes().get("WEBSOCKET_USERID").toString();
        logger.info("......AfterConnectionEstablished......");
        logger.info("session.getId:" + session.getId());
        logger.info("session.getLocalAddress:" + session.getLocalAddress().toString());
        logger.info("userId:" + userId);
        //websocket连接后记录连接信息
        if (users.keySet().contains(userId)) {
            CopyOnWriteArraySet<WebSocketSession> webSocketSessions = users.get(userId);
            webSocketSessions.add(session);
        } else {
            CopyOnWriteArraySet<WebSocketSession> webSocketSessions = new CopyOnWriteArraySet<>();
            webSocketSessions.add(session);
            users.put(userId, webSocketSessions);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        removeUserSession(session);
        if (session.isOpen()) {
            session.close();
        }
        logger.info("异常出现handleTransportError" + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        removeUserSession(session);
        logger.info("关闭afterConnectionClosed" + closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给符合要求的在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(String message, List<String> userIds) throws IOException {
        if (StringUtils.isEmpty(message) || CollectionUtils.isEmpty(userIds)) {
            return;
        }
        if (users.isEmpty()) {
            return;
        }
        for (String userId : userIds) {
            if (!users.keySet().contains(userId)) {
                continue;
            }
            CopyOnWriteArraySet<WebSocketSession> webSocketSessions = users.get(userId);
            if (webSocketSessions == null) {
                continue;
            }
            for (WebSocketSession webSocketSession : webSocketSessions) {
                if (webSocketSession.isOpen()) {
                    try {
                        webSocketSession.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        logger.error(" WebSocket server send message ERROR " + e.getMessage());
                        try {
                            throw e;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * websocket清除连接信息
     *
     * @param session
     */
    private void removeUserSession(WebSocketSession session) {
        String userId = session.getAttributes().get("WEBSOCKET_USERID").toString();
        if (users.keySet().contains(userId)) {
            CopyOnWriteArraySet<WebSocketSession> webSocketSessions = users.get(userId);
            webSocketSessions.remove(session);
            if (webSocketSessions.isEmpty()) {
                users.remove(userId);
            }
        }
    }
}
