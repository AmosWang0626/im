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
    /**
     * 命令
     */
    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;
    byte MESSAGE_REQUEST = 3;
    byte MESSAGE_RESPONSE = 4;


    /**
     * 根据命令获取class
     *
     * @param command 命令
     * @return class
     */
    static Class<? extends BasePacket> getRequestType(byte command) {
        Class<? extends BasePacket> clazz = null;
        switch (command) {
            case LOGIN_REQUEST:
                clazz = LoginRequest.class;
                break;
            case LOGIN_RESPONSE:
                clazz = LoginResponse.class;
                break;
            case MESSAGE_REQUEST:
                clazz = MessageRequest.class;
                break;
            case MESSAGE_RESPONSE:
                clazz = MessageResponse.class;
                break;
            default:
                break;
        }

        return clazz;
    }
}
