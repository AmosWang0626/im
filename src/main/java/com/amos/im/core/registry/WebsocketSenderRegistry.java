package com.amos.im.core.registry;


import com.alibaba.fastjson.JSON;
import com.amos.im.common.WebSocketSender;
import com.amos.im.common.general.GeneralResponse;
import com.amos.im.model.vo.OnlineUserVO;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 模块名称: im
 * 模块描述: WebsocketSenderRegistry
 *
 * @author amos.wang
 * @date 2020/7/9 13:44
 */
public interface WebsocketSenderRegistry {

    Map<String, WebSocketSender> TOKEN_SENDER_MAP = new HashMap<>();


    /**
     * 在线用户列表
     *
     * @return TOKEN 集合
     */
    static Set<String> online() {
        return TOKEN_SENDER_MAP.keySet();
    }

    /**
     * 在线用户列表排除指定token
     *
     * @param token 要排除的token
     * @return TOKEN 集合
     */
    static Set<String> onlineExclude(String token) {
        return online().stream().filter(s -> !s.equals(token)).collect(Collectors.toSet());
    }

    /**
     * 新增
     *
     * @param token  token
     * @param sender WebSocketSession
     */
    static void put(String token, WebSocketSender sender) {
        TOKEN_SENDER_MAP.put(token, sender);
        noticeOnlineUser();
    }

    /**
     * 获取
     *
     * @param token token
     * @return WebSocketSession
     */
    static WebSocketSender get(String token) {
        return TOKEN_SENDER_MAP.get(token);
    }

    /**
     * 删除
     *
     * @param token token
     */
    static void remove(String token) {
        TOKEN_SENDER_MAP.remove(token);
        noticeOnlineUser();
    }

    /**
     * 是否在线
     *
     * @param token token
     * @return true 存在
     */
    static boolean containsKey(String token) {
        return TOKEN_SENDER_MAP.containsKey(token);
    }

    /**
     * 用户登录或退出，通知所有用户
     */
    static void noticeOnlineUser() {
        WebsocketSenderRegistry.TOKEN_SENDER_MAP.forEach((token, sender) -> {
            OnlineUserVO onlineUserVO = new OnlineUserVO().setTokens(WebsocketSenderRegistry.onlineExclude(token));
            sender.send(JSON.toJSONString(new GeneralResponse<>(onlineUserVO)));
        });
    }
}
