package com.amos.im.controller.request;

/**
 * PROJECT: im
 * DESCRIPTION: 命令集
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
public interface Command {

    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;
    byte MESSAGE_REQUEST = 3;
    byte MESSAGE_RESPONSE = 4;
    byte CREATE_GROUP_REQUEST = 5;
    byte CREATE_GROUP_RESPONSE = 6;
    byte ENTER_GROUP_REQUEST = 7;
    byte ENTER_GROUP_RESPONSE = 8;

}
