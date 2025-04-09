package com.ssspil.websocket.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class StompEventListener {

    // 실시간 연결된 사용자 세션 ID들
    private final ConcurrentHashMap<String, String> sessionMap = new ConcurrentHashMap<>();

    // 모든 세션 ID
    public Set<String> getUsersInfo() {
        return sessionMap.keySet();
    }

    // 소켓 연결 신청
    @EventListener
    public void handleConnect(SessionConnectEvent sessionConnectEvent) {
        log.info("sessionConnectEvent : {}", sessionConnectEvent);
    }

    // 소켓 연결 완료
    @EventListener
    public void handleConnected(SessionConnectedEvent sessionConnectedEvent) {
        log.info("sessionConnectedEvent : {}", sessionConnectedEvent);
        String sessionId = sessionConnectedEvent.getMessage().getHeaders().get("simpSessionId").toString();
        sessionMap.put(sessionId, sessionId);
    }

    // 사용자가 구독
    @EventListener
    public void handleSubscribe(SessionSubscribeEvent sessionSubscribeEvent) {
        log.info("sessionSubscribeEvent : {}", sessionSubscribeEvent);
    }

    // 사용자가 구독 취소
    @EventListener
    public void handleUnsubscribe(SessionUnsubscribeEvent sessionUnsubscribeEvent) {
        log.info("sessionUnsubscribeEvent : {}", sessionUnsubscribeEvent);
    }

    // 소켓 연결 끊기
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent sessionDisconnectEvent) {
        log.info("sessionDisconnectEvent : {}", sessionDisconnectEvent);
        String sessionId = sessionDisconnectEvent.getMessage().getHeaders().get("simpSessionId").toString();
        sessionMap.remove(sessionId);
    }


}
