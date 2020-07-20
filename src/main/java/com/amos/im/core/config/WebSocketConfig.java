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
        // 消息传回客户端前缀，匹配 @SendTo 的前缀
        config.enableSimpleBroker("/server", "/client");
        // 消息映射前缀，默认为 @MessageMapping 追加前缀
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 有些浏览器不支持WebSocket, 故启用SockJS后备选项，SockJS优先使用原生WebSocket；
        // 如果在不支持websocket的浏览器中，会自动降级为轮询的方式（websocket/xhr-streaming/xhr-polling等）；
        // SockJS所处理的URL是“http://”或“https://”模式，而不是“ws://”和“wss://”，服务器通过withSockJS()方法来使用SockJS作为备用方法
        registry.addEndpoint("/im-ws").setAllowedOrigins("*").withSockJS();
    }

}
