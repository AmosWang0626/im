package com.amos.im.controller.request;

import com.amos.im.common.BasePacket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class GroupJoinRequest extends BasePacket {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.GROUP_JOIN_REQUEST;
    }
}
