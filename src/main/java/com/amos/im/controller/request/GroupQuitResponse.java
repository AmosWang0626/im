package com.amos.im.controller.request;

import com.amos.im.common.BasePacket;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PROJECT: im
 * DESCRIPTION: 退出群聊Response
 *
 * @author amos
 * @date 2019/4/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupQuitResponse extends BasePacket {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.GROUP_QUIT_RESPONSE;
    }
}
