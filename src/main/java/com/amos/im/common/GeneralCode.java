package com.amos.im.common;

/**
 * PROJECT: im
 * DESCRIPTION: GeneralCode
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public enum GeneralCode {
    /**
     * 通用
     */
    SUCCESS("100", "成功!"),
    FAIL("101", "失败"),
    ERROR_PARAM("102", "参数错误"),
    ILLEGAL_REQUEST("103", "非法请求"),
    /**
     * 群聊
     */
    CREATE_GROUP_FAIL("302", "群内不能没有成员"),
    GROUP_NOT_EXIST("303", "群聊不存在"),
    YOU_HAVE_JOINED_THE_GROUP("304", "您已加入该群聊"),
    ALONE_BOTH_LOGIN("305", "对方不在线"),
    /**
     * 登录相关
     */
    LOGIN_FAIL_PASSWORD_EMPTY("401", "用户名/密码不能为空"),
    LOGIN_FAIL_PASSWORD_ERROR("402", "账号或密码错误"),
    LOGIN_FAIL_USERNAME_EXIST("403", "用户名已被占用, 请使用其他用户名登录!"),
    ;

    private final String code;
    private String msg;

    GeneralCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public GeneralCode setMsg(String msg) {
        this.msg += msg;
        return this;
    }

}
