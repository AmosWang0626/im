package com.amos.im.handler;


import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

/**
 * 模块名称: im
 * 模块描述: AbcRegistry
 *
 * @author amos.wang
 * @date 2020/7/9 13:44
 */
public interface AbcRegistry {

    Map<String, WebSocketSession> TOKEN_SESSION_MAP = new HashMap<>();


    static void put(String token, WebSocketSession session) {
        TOKEN_SESSION_MAP.put(token, session);
    }

    static WebSocketSession get(String token) {
        return TOKEN_SESSION_MAP.get(token);
    }

    static void remove(String token) {
        TOKEN_SESSION_MAP.remove(token);
    }

    static boolean containsKey(String token) {
        return TOKEN_SESSION_MAP.containsKey(token);
    }

}
