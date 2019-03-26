package com.amos.im.controller.request;

import com.amos.im.common.BasePacket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
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
public class CreateGroupRequest extends BasePacket {

    private String sponsor;

    private String groupName;

    private List<String> tokenList;

    private Date createTime;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
