package com.amos.im.controller.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.common.attribute.AttributeGroupUtil;
import com.amos.im.controller.request.GroupQuitRequest;
import com.amos.im.controller.request.GroupQuitResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
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
@ChannelHandler.Sharable
public class GroupQuitRequestHandler extends SimpleChannelInboundHandler<GroupQuitRequest> {

    public static final GroupQuitRequestHandler INSTANCE = new GroupQuitRequestHandler();

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, GroupQuitRequest request) throws Exception {
        Channel inxChannel = ctx.channel();

        String groupId = request.getGroupId();
        ChannelGroup channels = AttributeGroupUtil.getChannelGroup(groupId);

        GroupQuitResponse response = new GroupQuitResponse();

        if (channels == null) {
            response.setSuccess(false);
            response.setGeneralCode(GeneralCode.GROUP_NOT_EXIST);
            inxChannel.writeAndFlush(response);
            return;
        }

        channels.remove(ctx.channel());
        AttributeGroupUtil.updateGroupServer(groupId, channels);
        if (channels.size() == 0) {
            AttributeGroupUtil.removeGroupServer(groupId);
        }

        response.setSuccess(true);
        response.setGroupId(groupId);
        inxChannel.writeAndFlush(response);
    }

}
