package com.amos.im.common;

import com.amos.im.request.LoginPacket;

import java.util.HashMap;
import java.util.Map;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
public class ResourceMap {

    private static final Map<Byte, Class<? extends BasePacket>> PACKET_MAP;

    static {
        PACKET_MAP = new HashMap<>();
        PACKET_MAP.put(Command.LOGIN_REQUEST, LoginPacket.class);

    }

    /**
     * 根据命令获取class
     *
     * @param command 命令
     * @return class
     */
    public static Class<? extends BasePacket> getRequestType(byte command) {
        return PACKET_MAP.get(command);
    }

}
