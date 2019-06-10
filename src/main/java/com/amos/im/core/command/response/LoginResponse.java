package com.amos.im.core.command.response;

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
     * 用户名
     */
    private String username;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

}
