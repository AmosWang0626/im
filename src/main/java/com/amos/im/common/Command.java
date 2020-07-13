package com.amos.im.common;

/**
 * PROJECT: im
 * DESCRIPTION: 命令集
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
public interface Command {

    /**
     * REQUEST
     */
    byte LOGIN_REQUEST = 1;
    byte MESSAGE_REQUEST = 3;
    byte GROUP_CREATE_REQUEST = 5;
    byte GROUP_JOIN_REQUEST = 7;
    byte GROUP_LIST_REQUEST = 9;
    byte GROUP_QUIT_REQUEST = 11;
    byte GROUP_MESSAGE_REQUEST = 13;

    /**
     * RESPONSE
     */
    byte LOGIN_RESPONSE = 2;
    byte MESSAGE_RESPONSE = 4;
    byte GROUP_CREATE_RESPONSE = 6;
    byte GROUP_JOIN_RESPONSE = 8;
    byte GROUP_LIST_RESPONSE = 10;
    byte GROUP_QUIT_RESPONSE = 12;
    byte GROUP_MESSAGE_RESPONSE = 14;
    byte ONLINE_USER_RESPONSE = 15;

}
