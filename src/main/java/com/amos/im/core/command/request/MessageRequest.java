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
 * DESCRIPTION: MessageRequest
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("单聊消息Model")
public class MessageRequest extends BasePacket {

    @ApiModelProperty("收消息人Token")
    private String toToken;

    @ApiModelProperty("单聊消息")
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

}
