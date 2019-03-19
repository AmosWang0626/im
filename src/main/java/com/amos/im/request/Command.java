package com.amos.im.request;

import com.amos.im.common.BasePacket;

/**
 * PROJECT: im
 * DESCRIPTION: 命令集
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
public interface Command {

    byte LOGIN_REQUEST = 1;

    /**
     * 根据命令获取class
     *
     * @param command 命令
     * @return class
     */
    static Class<? extends BasePacket> getRequestType(byte command) {
        switch (command) {
            case LOGIN_REQUEST:
                return LoginPacket.class;
            default:
                return null;
        }
    }
}
