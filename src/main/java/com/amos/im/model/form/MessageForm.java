package com.amos.im.model.form;

import com.amos.im.common.BasePacket;
import com.amos.im.common.Command;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: 单聊消息Model
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@Getter
@Setter
@Accessors(chain = true)
public class MessageForm extends BasePacket {

    /**
     * 收消息人Token
     */
    private String receiver;
    /**
     * 单聊消息
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

}
