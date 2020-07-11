package com.amos.im.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

/**
 * DESCRIPTION: WebSocketSender
 * 由于 WebSocketSession#send() 仅能在 WebSocketHandler#handle() 方法内部使用，故封装此类
 * 参考: <a href="https://my.oschina.net/bianxin/blog/3082013"></a>
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/7/11
 */
@Getter
@Setter
public class WebSocketSender {

    private WebSocketSession session;

    private FluxSink<WebSocketMessage> sink;

    public WebSocketSender(WebSocketSession session, FluxSink<WebSocketMessage> sink) {
        this.session = session;
        this.sink = sink;
    }

    public void send(String data) {
        sink.next(session.textMessage(data));
    }

}
