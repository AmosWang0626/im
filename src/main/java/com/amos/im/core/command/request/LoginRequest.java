package com.amos.im.core.command.request;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("登录Model")
public class LoginRequest extends BasePacket {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
