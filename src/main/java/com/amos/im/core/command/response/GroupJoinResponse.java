package com.amos.im.core.command.response;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import com.amos.im.core.vo.GroupInfoVO;
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
public class GroupJoinResponse extends BasePacket {

    private String username;

    private GroupInfoVO groupInfoVO;

    @Override
    public Byte getCommand() {
        return Command.GROUP_JOIN_RESPONSE;
    }
}
