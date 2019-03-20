package com.amos.im.common;

/**
 * PROJECT: im
 * DESCRIPTION: GeneralCode
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public enum GeneralCode {
    /***/
    SUCCESS("100", "成功!"),
    FAIL("101", "失败"),
    ERROR_PARAM("102", "参数错误"),
    ILLEGAL_REQUEST("103", "非法请求"),
    LOGIN_FAIL("301", "账号或密码错误"),
    ;

    private final String code;
    private final String msg;

    GeneralCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }}
