package com.amos.im.core.response;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
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
public class GroupCreateResponse extends BasePacket {

    private String groupId;

    private String groupName;

    private String sponsorName;

    private List<String> nicknameList;

    @Override
    public Byte getCommand() {
        return Command.GROUP_CREATE_RESPONSE;
    }

}
