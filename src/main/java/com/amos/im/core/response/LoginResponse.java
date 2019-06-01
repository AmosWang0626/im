package com.amos.im.core.response;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: LoginResponse
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
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
