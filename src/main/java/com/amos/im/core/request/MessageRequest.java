package com.amos.im.core.request;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
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
public class MessageRequest extends BasePacket {

    private String toToken;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

}
