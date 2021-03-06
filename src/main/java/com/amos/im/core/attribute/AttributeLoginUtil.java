package com.amos.im.core.attribute;

import com.amos.im.core.pojo.vo.LoginInfoVO;
import io.netty.channel.Channel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public class AttributeLoginUtil {

    /**
     * 维护一个 [token >>> Channel] 的映射Map
     */
    private static final Map<String, Channel> CHANNEL_TOKEN_MAP = new ConcurrentHashMap<>();
    /**
     * 维护一个 [token >>> UserInfoVO] 的映射Map, 供获取在线用户
     */
    private static final Map<String, LoginInfoVO> TOKEN_USER_INFO_MAP = new ConcurrentHashMap<>();
    /**
     * 维护一个 [username >>> Channel] 的映射Map, 限制用户名重复使用
     */
    private static final Map<String, Channel> CHANNEL_USERNAME_MAP = new ConcurrentHashMap<>();


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
        CHANNEL_TOKEN_MAP.put(token, channel);
        CHANNEL_USERNAME_MAP.put(username, channel);
        LoginInfoVO loginInfo = new LoginInfoVO().setToken(token).setUsername(username).setCreateTime(LocalDateTime.now());
        TOKEN_USER_INFO_MAP.put(token, loginInfo);
        channel.attr(ImAttribute.LOGIN_INFO).set(new LoginInfoVO().setToken(token).setUsername(username));
    }

    /**
     * 解绑登录token
     */
    public static void unBindToken(Channel channel) {
        if (hasLogin(channel)) {
            LoginInfoVO loginInfo = getLoginInfo(channel);
            CHANNEL_USERNAME_MAP.remove(loginInfo.getUsername());
            CHANNEL_TOKEN_MAP.remove(loginInfo.getToken());
            TOKEN_USER_INFO_MAP.remove(loginInfo.getToken());
            channel.attr(ImAttribute.LOGIN_INFO).set(null);
        }
    }

    /**
     * 根据channel获取登录信息
     */
    public static LoginInfoVO getLoginInfo(Channel channel) {
        return channel.attr(ImAttribute.LOGIN_INFO).get();
    }

    /**
     * 根据token获取用户信息
     */
    public static LoginInfoVO getUserInfo(String token) {
        return TOKEN_USER_INFO_MAP.get(token);
    }

    /**
     * 根据 token 获取 channel
     */
    public static Channel getChannel(String token) {
        return CHANNEL_TOKEN_MAP.get(token);
    }

    /**
     * 根据 username 获取 channel
     */
    public static Channel getChannelByUsername(String username) {
        return CHANNEL_USERNAME_MAP.get(username);
    }

    /**
     * 根据 username 获取 channel
     */
    public static List<LoginInfoVO> onlineList() {
        return new ArrayList<>(TOKEN_USER_INFO_MAP.values());
    }

}
