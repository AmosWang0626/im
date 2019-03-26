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
    /**
     * 创建时间
     */
    private Date createTime;

    @Override
    public Byte getCommand() {
        return Command.GROUP_CREATE_REQUEST;
    }
}
