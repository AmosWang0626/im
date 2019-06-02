package com.amos.im.core.request;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PROJECT: im
 * DESCRIPTION: 退出群聊Request
 *
 * @author amos
 * @date 2019/4/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("退出群Model")
public class GroupQuitRequest extends BasePacket {

    @ApiModelProperty("群聊ID")
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.GROUP_QUIT_REQUEST;
    }
}
