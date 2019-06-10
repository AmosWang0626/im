package com.amos.im.core.command.request;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("创建群聊Model")
public class GroupCreateRequest extends BasePacket {
    /**
     * 发起人token
     */
    private String sponsor;
    /**
     * 群聊名称
     */
    private String groupName;
    /**
     * token 列表
     */
    private List<String> tokenList;

    @Override
    public Byte getCommand() {
        return Command.GROUP_CREATE_REQUEST;
    }
}
