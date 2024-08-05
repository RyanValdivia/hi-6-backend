package com.tdl.hi6.sockets;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class WebSocketService extends TextWebSocketHandler {
    private final CopyOnWriteArrayList<WebSocketSession> sessions;

    @Override
    public void afterConnectionEstablished (WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed (WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage (WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession webSession : sessions) {
            webSession.sendMessage(message);
        }
    }
}
