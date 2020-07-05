package com.amos.im.core.command.request;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: GroupMessageRequest
 *
 * @author Daoyuan
 * @date 2019/4/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("群聊消息Model")
public class GroupMessageRequest extends BasePacket {

    @ApiModelProperty("收消息群ID")
    private String groupId;

    @ApiModelProperty("群聊消息")
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }

}
