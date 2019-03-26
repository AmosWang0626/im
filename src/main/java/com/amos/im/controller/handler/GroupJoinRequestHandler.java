package com.amos.im.controller.handler;

import com.amos.im.common.attribute.AttributeGroupUtil;
import com.amos.im.controller.request.GroupJoinRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class GroupJoinRequestHandler extends SimpleChannelInboundHandler<GroupJoinRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequest groupJoinRequest) throws Exception {
        String groupId = groupJoinRequest.getGroupId();
        ChannelGroup channelGroup = AttributeGroupUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            System.out.println("群聊" + groupId + "不存在");
        }


    }

}
