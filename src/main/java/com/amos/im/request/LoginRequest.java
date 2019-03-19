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
public class LoginRequest extends BasePacket {

    private String phoneNo;

    private String password;

    private String verifyCode;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
