package com.amos.im.handler;

import com.alibaba.fastjson.JSONObject;
import com.amos.im.core.command.request.MessageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * DESCRIPTION: 测试 Handler
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/7/8
 */
@Component
public class ChatHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String query = session.getHandshakeInfo().getUri().getQuery();
        Map<String, String> queryMap = getUrlParams(query);
        String token = queryMap.getOrDefault("token", "");

        AbcRegistry.put(token, session);

        return session
                .receive()
                .flatMap(webSocketMessage -> {
                    String payload = webSocketMessage.getPayloadAsText();
                    System.out.println(AbcRegistry.TOKEN_SESSION_MAP);
                    MessageRequest messageRequest = JSONObject.parseObject(payload, MessageRequest.class);
                    String receiverToken = messageRequest.getReceiver();
                    Optional<WebSocketSession> receiverSession = Optional.ofNullable(AbcRegistry.get(receiverToken));

                    return receiverSession
                            .<org.reactivestreams.Publisher<? extends Void>>map(webSocketSession ->
                                    webSocketSession.send(Mono.just(webSocketSession.textMessage(messageRequest.getMessage()))))
                            .orElseGet(() -> session.send(Mono.just(session.textMessage("目标用户不在线"))));
                }).then();
//                .doFinally(signal -> AbcRegistry.remove(token));
    }


    /**
     * 根据 URL后置参数获取 对应参数 Map
     *
     * @param params URL后置参数
     * @return 参数 Map
     */
    private Map<String, String> getUrlParams(String params) {
        if (StringUtils.isBlank(params)) {
            return Collections.emptyMap();
        }

        String[] queryParam = params.split("&");
        Map<String, String> queryMap = new HashMap<>(queryParam.length);
        Arrays.stream(queryParam).forEach(s -> {
            String[] kv = s.split("=", 2);
            String value = kv.length == 2 ? kv[1] : "";
            queryMap.put(kv[0], value);
        });

        return queryMap;
    }

}
