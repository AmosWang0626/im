package com.amos.im.controller.request;

import com.amos.im.common.BasePacket;
import com.amos.im.controller.dto.GroupInfoVO;
import com.amos.im.controller.dto.LoginInfoVO;
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

    private List<LoginInfoVO> loginInfoVOS;

    @Override
    public Byte getCommand() {
        return Command.GROUP_LIST_RESPONSE;
    }
}
