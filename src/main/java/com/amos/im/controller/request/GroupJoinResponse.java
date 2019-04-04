package com.amos.im.controller.request;

import com.amos.im.common.BasePacket;
import com.amos.im.controller.dto.GroupInfoVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

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

    private String nickName;

    private Boolean success;

    private GroupInfoVO groupInfoVO;

    private Date createTime;

    @Override
    public Byte getCommand() {
        return Command.GROUP_JOIN_RESPONSE;
    }
}
