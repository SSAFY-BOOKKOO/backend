package com.ssafy.bookkoo.booktalkservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지를 발행할 URL prefix
        registry.setApplicationDestinationPrefixes("/booktalks/pub");
        // 메세지를 구독할 URL prefix
        registry.enableSimpleBroker("/booktalks/sub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // ws 연결을 위한 엔드포인트
        registry.addEndpoint("/booktalks/connect")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
