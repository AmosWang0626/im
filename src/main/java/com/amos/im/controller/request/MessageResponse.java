package com.amos.im.controller.request;

import com.amos.im.common.BasePacket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

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
    /**
     * 发送的时间
     */
    private Date createTime;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }

}
