package com.amos.im.controller.console;

import com.amos.im.controller.request.GroupJoinRequest;
import io.netty.channel.Channel;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/26
 */
public class ConsoleCmdGroupJoin extends BaseConsole {

    @Override
    void exec(Channel channel) {
        System.out.print("请输入群聊ID: ");
        String groupId = sc.next();

        GroupJoinRequest groupJoinRequest = new GroupJoinRequest();
        groupJoinRequest.setGroupId(groupId);

        channel.writeAndFlush(groupJoinRequest);
    }

}
