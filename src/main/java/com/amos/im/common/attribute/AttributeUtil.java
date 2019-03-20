package com.amos.im.common.attribute;

import io.netty.channel.Channel;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public class AttributeUtil {

    public static void maskLogin(Channel channel) {
        channel.attr(AttributeConstant.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Boolean loginSuccess = channel.attr(AttributeConstant.LOGIN).get();
        return loginSuccess != null && loginSuccess;
    }

    public static void maskToken(Channel channel, String token) {
        channel.attr(AttributeConstant.TOKEN).set(token);
    }

    public static String getToken(Channel channel) {
        return channel.attr(AttributeConstant.TOKEN).get();
    }

}
