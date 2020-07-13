package com.amos.im.core.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * DESCRIPTION: 测试 Handler
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/7/8
 */
@Component
public class EchoHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> map = session.receive().map(webSocketMessage -> {
            String message = webSocketMessage.getPayloadAsText();
            return "Echo回复：" + message;
        }).map(session::textMessage);

        return session.send(map);
    }

}
