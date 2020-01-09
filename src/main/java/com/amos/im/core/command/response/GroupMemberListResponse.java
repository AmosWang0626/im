package com.amos.im.core.command.response;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import com.amos.im.core.vo.GroupInfoVO;
import com.amos.im.core.vo.LoginInfoVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * PROJECT: im
 * DESCRIPTION: 群成员列表Response
 *
 * @author amos
 * @date 2019/4/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupMemberListResponse extends BasePacket {

    private GroupInfoVO groupInfoVO;

    private List<LoginInfoVO> loginInfoList;

    @Override
    public Byte getCommand() {
        return Command.GROUP_LIST_RESPONSE;
    }
}
