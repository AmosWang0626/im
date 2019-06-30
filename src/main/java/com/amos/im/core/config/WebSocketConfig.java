package com.amos.im.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * PROJECT: Sales
 * DESCRIPTION: WebSocketConfig(Broker即代理)
 *
 * @author amos
 * @date 2019/6/30
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 消息传回客户端前缀，也即 @SendTo 前缀
        config.enableSimpleBroker("/ws");
        // 消息映射前缀，也即 @MessageMapping 前缀
        config.setApplicationDestinationPrefixes("/im");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 启用SockJS后备选项，以便在WebSocket不可用时可以使用备用传输
        // SockJS客户端将尝试连接到“/im”并使用可用的最佳传输（websocket/xhr-streaming/xhr-polling等）
        registry.addEndpoint("/im").withSockJS();
    }


}
