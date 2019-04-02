package com.amos.im.common.attribute;

import com.amos.im.controller.dto.LoginVO;
import io.netty.channel.Channel;

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
     * 维护一个 [Token >>> Channel] 的映射Map
     */
    private static final Map<String, Channel> CHANNEL_TOKEN_MAP = new ConcurrentHashMap<>();

    /**
     * 是否已登录
     */
    public static boolean hasLogin(Channel channel) {
        return channel.attr(AttributeConstant.LOGIN_INFO).get() != null;
    }

    /**
     * 保存登录token
     */
    public static void bindToken(Channel channel, String token, String name) {
        CHANNEL_TOKEN_MAP.put(token, channel);
        channel.attr(AttributeConstant.LOGIN_INFO).set(new LoginVO().setToken(token).setNickname(name));
    }

    /**
     * 解绑登录token
     */
    public static void unBindToken(Channel channel) {
        if (hasLogin(channel)) {
            CHANNEL_TOKEN_MAP.remove(getLoginInfo(channel).getToken());
            channel.attr(AttributeConstant.LOGIN_INFO).set(null);
        }
    }

    /**
     * 根据channel获取登录信息
     */
    public static LoginVO getLoginInfo(Channel channel) {
        return channel.attr(AttributeConstant.LOGIN_INFO).get();
    }

    /**
     * 根据token获取channel
     */
    public static Channel getChannel(String token) {
        return CHANNEL_TOKEN_MAP.get(token);
    }

}
