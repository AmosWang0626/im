package com.amos.im.core.command.response;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: MessageResponse
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class MessageResponse extends BasePacket {

    /**
     * 发送人昵称
     */
    private String nickName;
    /**
     * 发送人token
     */
    private String fromToken;
    /**
     * 发送的消息
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }

}
