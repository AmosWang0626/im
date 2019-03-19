package com.amos.im.request;

import com.amos.im.common.BasePacket;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends BasePacket {
    /**
     * 用户唯一标识
     */
    private String token;
    /**
     * 昵称[默认手机号后四位]
     */
    private String nickname;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

}
