package com.bsc.stokoin.config.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Override // 메시지 브로커가 해당하는 클라이언트에게 메시지를 전달할 때 사용할 prefix를 정의한다.
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override // 클라이언트가 웹 소켓 서버에 연결하는데 사용할 웹 소켓 엔드포인트를 등록한다.
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();// 웹 소켓 연결 endpoint는 /ws/chat이며, SockJS를 사용한다.
    }
}