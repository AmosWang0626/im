package com.amos.im.request;

import com.amos.im.common.BasePacket;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageResponse extends BasePacket {

    private String message;

    private Date createTime;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
