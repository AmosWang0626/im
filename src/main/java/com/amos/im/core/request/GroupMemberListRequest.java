package com.amos.im.core.request;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PROJECT: im
 * DESCRIPTION: 获取用户列表Request
 *
 * @author amos
 * @date 2019/4/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("群聊成员Model")
public class GroupMemberListRequest extends BasePacket {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.GROUP_LIST_REQUEST;
    }
}
