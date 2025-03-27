package com.ssspil.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // STOMP 엔드포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 이 소켓에 연결할 주소
        registry.addEndpoint("/ws")     // 경로 여러개 추가 가능
                .setAllowedOrigins("http://localhost:3000") // 경로 여러개 추가 가능
                .addInterceptors()  // TODO HandshakeInterceptor 구현하여  사용자 인증 + 연결 경로 유효성 검사
                .withSockJS();
    }

    // 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트가 웹소켓을 받을 Prefix 엔트포인트 = 구독 주소
        registry.enableSimpleBroker("/chat/room",       // 채팅
                                                    "/notification"      // 알림
        );

        // 클라이언트가 웹소켓 보낼 때 사용하는 경로 = 메시지 전송 주소
        registry.setApplicationDestinationPrefixes("/message");
    }


    // 클라이언트 -> 서버 메시지 인터셉터 추가
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                log.info("클라이언트가 보낸 메시지 : {}", message);
                return message; // 메시지 로깅
            }
        });
    }

    // 서버 -> 클라이언트 메시지 인터셉터 추가
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                log.info("서버가 보내는 메시지 {}", message);
                return message; // 메시지 로깅
            }
        });
    }

    // 메시지 크기 제한, 버퍼 크기, 타임아웃 등의 설정
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
//        registry.setMessageSizeLimit(8192)  // 메시지 최대 크기 (8KB) (기본 값 : 64KB (65536 bytes))
//                .setSendBufferSizeLimit(8192) // 전송 버퍼 크기 제한 (기본 값 512KB (524288 bytes))
//                .setTimeToFirstMessage(5000); // 최초 메시지 수신 대기 시간 (5초) (제한 없음, 기본값 없음)
//    }
}
