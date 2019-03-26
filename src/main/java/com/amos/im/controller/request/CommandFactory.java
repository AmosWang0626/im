package com.amos.im.controller.request;

import com.amos.im.common.BasePacket;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public class CommandFactory {

    /**
     * 根据命令获取class
     *
     * @param command 命令
     * @return class
     */
    public static Class<? extends BasePacket> getRequestType(byte command) {
        Class<? extends BasePacket> clazz = null;

        switch (command) {
            case Command.LOGIN_REQUEST:
                clazz = LoginRequest.class;
                break;

            case Command.LOGIN_RESPONSE:
                clazz = LoginResponse.class;
                break;

            case Command.MESSAGE_REQUEST:
                clazz = MessageRequest.class;
                break;

            case Command.MESSAGE_RESPONSE:
                clazz = MessageResponse.class;
                break;

            case Command.CREATE_GROUP_REQUEST:
                clazz = CreateGroupRequest.class;
                break;

            case Command.CREATE_GROUP_RESPONSE:
                clazz = CreateGroupResponse.class;
                break;

            default:
                break;
        }

        return clazz;
    }

}
