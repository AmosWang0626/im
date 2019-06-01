package com.amos.im.core.request;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: GroupMessageRequest
 *
 * @author Daoyuan
 * @date 2019/4/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class GroupMessageRequest extends BasePacket {

    private String toGroup;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }

}
