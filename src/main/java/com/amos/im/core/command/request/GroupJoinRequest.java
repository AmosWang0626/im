package com.amos.im.core.command.request;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import io.swagger.annotations.ApiModel;
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
@ApiModel("加入群聊Model")
public class GroupJoinRequest extends BasePacket {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.GROUP_JOIN_REQUEST;
    }
}
