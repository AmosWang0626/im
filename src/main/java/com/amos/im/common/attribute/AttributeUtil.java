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
public class AttributeUtil {

    /**
     * 维护一个 [Token >>> Channel] 的映射Map
     */
    private static final Map<String, Channel> TOKEN_CHANNEL_MAP = new ConcurrentHashMap<>();


    public static boolean hasLogin(Channel channel) {
        return channel.attr(AttributeConstant.TOKEN).get() != null;
    }

    public static void bindToken(Channel channel, String token, String name) {
        TOKEN_CHANNEL_MAP.put(token, channel);
        channel.attr(AttributeConstant.TOKEN).set(new LoginVO().setToken(token).setNickname(name));
    }

    public static void unBindToken(Channel channel) {
        if (hasLogin(channel)) {
            TOKEN_CHANNEL_MAP.remove(getToken(channel).getToken());
            channel.attr(AttributeConstant.TOKEN).set(null);
            System.out.println(TOKEN_CHANNEL_MAP);
        }
    }

    public static LoginVO getToken(Channel channel) {
        return channel.attr(AttributeConstant.TOKEN).get();
    }

    public static Channel getChannel(String token) {
        return TOKEN_CHANNEL_MAP.get(token);
    }

}
