package com.amos.im.request;

import com.amos.im.common.BasePacket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: LoginRequest
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class LoginRequest extends BasePacket {

    private String phoneNo;

    private String password;

    private String verifyCode;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
