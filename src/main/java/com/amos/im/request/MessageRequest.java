package com.amos.im.request;

import com.amos.im.common.BasePacket;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * PROJECT: im
 * DESCRIPTION: MessageRequest
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageRequest extends BasePacket {

    private String message;

    private Date createTime;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
