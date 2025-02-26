package com.erp.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 발행자 - 메시지 브로커(관리) - 구독자 (전송 방식)
    @Override
    public void configureMessageBroker(MessageBrokerRegistry mbr) {
        mbr.enableSimpleBroker("/one", "/all"); // 실시간 메시지 송수신
        // one => 1:1 , all => 1:n
        mbr.setApplicationDestinationPrefixes("/app"); // 클라이언트 요청 prefix
    }

    // 웹 소켓 엔드포인트 지정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry ser) {
        ser.addEndpoint("/ws-chat") // 클라이언트 websocket 연결
                .setAllowedOrigins("*")
                .withSockJS(); // SockJS 풀백 지원
    }
}
