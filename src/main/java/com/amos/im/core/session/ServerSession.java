package com.amos.im.core.session;

import com.alibaba.fastjson.JSON;
import com.amos.im.core.attribute.ImAttribute;
import com.amos.im.core.command.response.OnlineUserResponse;
import com.amos.im.core.pojo.vo.LoginInfoVO;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * DESCRIPTION: 服务端 channel
 *
 * @author <a href="mailto:amos.wang@xiaoi.com">amos.wang</a>
 * @date 1/13/2020
 */
public class ServerSession {

    /**
     * 维护一个 [token >>> Channel] 的映射Map
     */
    private static final Map<String, Channel> CHANNEL_TOKEN_MAP = new ConcurrentHashMap<>();
    /**
     * 维护一个 [token >>> UserInfoVO] 的映射Map, 供获取在线用户
     */
    private static final Map<String, LoginInfoVO> TOKEN_USER_INFO_MAP = new ConcurrentHashMap<>();
    /**
     * 用户名唯一
     */
    private static final Set<String> USERNAME_SET = new HashSet<>();


    /**
     * 是否已登录
     */
    public static boolean hasLogin(Channel channel) {
        return channel.attr(ImAttribute.LOGIN_INFO).get() != null;
    }

    /**
     * 保存登录token
     */
    public static void bindToken(Channel channel, String token, String username) {
        LoginInfoVO loginInfo = new LoginInfoVO().setToken(token).setUsername(username).setCreateTime(LocalDateTime.now());
        // 用户信息置入channel
        channel.attr(ImAttribute.TOKEN_INFO).set(token);
        channel.attr(ImAttribute.LOGIN_INFO).set(loginInfo);

        // 保存token、channel映射，保证消息转发
        CHANNEL_TOKEN_MAP.put(token, channel);

        // 用户名唯一
        USERNAME_SET.add(username);

        // 备份用户信息，防止channel关闭，用户信息丢失
        TOKEN_USER_INFO_MAP.put(token, loginInfo);

        notifyOnlineUsers();
    }

    /**
     * 重新绑定 token --> channel
     */
    public static void reBindToken(Channel channel, String token) {
        CHANNEL_TOKEN_MAP.put(token, channel);
        channel.attr(ImAttribute.TOKEN_INFO).set(token);
        channel.attr(ImAttribute.LOGIN_INFO).set(TOKEN_USER_INFO_MAP.get(token));

        notifyOnlineUsers();
    }

    /**
     * 解绑 channel
     */
    public static void unBindToken(Channel channel) {
        if (hasLogin(channel)) {
            LoginInfoVO loginInfo = getLoginInfo(channel);

            USERNAME_SET.remove(loginInfo.getUsername());
            CHANNEL_TOKEN_MAP.remove(loginInfo.getToken());

            notifyOnlineUsers();
        }
    }

    /**
     * 根据 channel 获取登录信息
     */
    public static LoginInfoVO getLoginInfo(Channel channel) {
        return channel.attr(ImAttribute.LOGIN_INFO).get();
    }

    /**
     * 根据 token 获取 LoginInfoVO
     */
    public static LoginInfoVO getLoginInfo(String token) {
        return TOKEN_USER_INFO_MAP.get(token);
    }

    /**
     * 根据 token 获取 channel
     */
    public static Channel getChannel(String token) {
        return CHANNEL_TOKEN_MAP.get(token);
    }

    /**
     * 用户名唯一
     */
    public static boolean existedUsername(String username) {
        return USERNAME_SET.contains(username);
    }

    /**
     * 获取所有在线用户的 LoginInfoVO
     */
    public static List<LoginInfoVO> onlineUserInfoList() {
        return new ArrayList<>(TOKEN_USER_INFO_MAP.values());
    }


    /**
     * 通知所有在线用户当前在线用户列表
     */
    private static void notifyOnlineUsers() {
        List<LoginInfoVO> loginInfoList = ServerSession.onlineUserInfoList();

        // 遍历所有在线用户的 Channel
        CHANNEL_TOKEN_MAP.values().forEach(channel -> {
            String currentChannelToken = channel.attr(ImAttribute.TOKEN_INFO).get();
            List<LoginInfoVO> onlineLoginInfoList = loginInfoList.stream()
                    .filter(loginInfoVO -> !loginInfoVO.getToken().equals(currentChannelToken))
                    .collect(Collectors.toList());

            OnlineUserResponse onlineUserResponse = new OnlineUserResponse().setLoginInfoList(onlineLoginInfoList);

            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(onlineUserResponse)));
        });
    }

}
