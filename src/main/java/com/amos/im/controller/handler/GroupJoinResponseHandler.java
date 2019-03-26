package com.amos.im.controller.handler;

import com.amos.im.controller.request.GroupJoinResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class GroupJoinResponseHandler extends SimpleChannelInboundHandler<GroupJoinResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinResponse groupJoinRequest) throws Exception {
        if (groupJoinRequest.getSuccess()) {
            System.out.println("加入群聊成功!");
        }


    }

}
