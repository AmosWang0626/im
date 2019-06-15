package com.amos.im.core.command.response;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: GroupMessageResponse
 *
 * @author Daoyuan
 * @date 2019/4/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class GroupMessageResponse extends BasePacket {

    /**
     * 发送人昵称
     */
    private String username;
    /**
     * 发送人token
     */
    private String fromGroup;
    /**
     * 发送的消息
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }

}
