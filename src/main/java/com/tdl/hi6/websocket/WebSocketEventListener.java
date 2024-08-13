package com.tdl.hi6.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final OnlineOfflineService onlineOfflineService;

    private final Map<String, String> simpSessionIdToSubscriptionId = new ConcurrentHashMap<>();

//    public void handleWebSocketDisconnectListener (SessionDisconnectEvent event) {
//        onlineOfflineService.removeOnlineUser(event.getUser());
//    }
//
//    @EventListener
//    @SendTo
//    public void handleSubscribeEvent(SessionSubscribeEvent sessionSubscribeEvent) {
//        String subscribedChannel =
//                (String) sessionSubscribeEvent.getMessage().getHeaders().get("simpDestination");
//        String simpSessionId =
//                (String) sessionSubscribeEvent.getMessage().getHeaders().get("simpSessionId");
//        if (subscribedChannel == null) {
//            return;
//        }
//        simpSessionIdToSubscriptionId.put(simpSessionId, subscribedChannel);
//        onlineOfflineService.addUserSubscribed(sessionSubscribeEvent.getUser(), subscribedChannel);
//    }
}
