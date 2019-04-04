package com.amos.im.controller.console;

import com.amos.im.controller.request.GroupMemberListRequest;
import io.netty.channel.Channel;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/26
 */
public class ConsoleCmdGroupList extends BaseConsole {

    @Override
    void exec(Channel channel) {
        System.out.print("请输入群聊ID: ");
        String groupId = sc.next();

        GroupMemberListRequest memberListRequest = new GroupMemberListRequest();
        memberListRequest.setGroupId(groupId);

        channel.writeAndFlush(memberListRequest);
    }

}
