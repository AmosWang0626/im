package com.amos.im.common.attribute;

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
public class AttributeUtil {

    /**
     * 维护一个 [Token >>> Channel] 的映射Map
     */
    private static final Map<String, Channel> TOKEN_CHANNEL_MAP = new ConcurrentHashMap<>();


    public static boolean hasLogin(Channel channel) {
        String token = channel.attr(AttributeConstant.TOKEN).get();
        return token != null;
    }

    public static void bindToken(Channel channel, String token) {
        TOKEN_CHANNEL_MAP.put(token, channel);
        channel.attr(AttributeConstant.TOKEN).set(token);
    }

    public static void unBindToken(Channel channel) {
        if (hasLogin(channel)) {
            TOKEN_CHANNEL_MAP.remove(getToken(channel));
            channel.attr(AttributeConstant.TOKEN).set(null);
        }
    }

    public static String getToken(Channel channel) {
        return channel.attr(AttributeConstant.TOKEN).get();
    }

    public static Channel getChannel(String token) {
        return TOKEN_CHANNEL_MAP.get(token);
    }

}
