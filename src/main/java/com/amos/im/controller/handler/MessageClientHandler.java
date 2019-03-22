package com.amos.im.controller.handler;

import com.amos.im.common.util.PrintUtil;
import com.amos.im.controller.request.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Daoyuan
 */
public class MessageClientHandler extends SimpleChannelInboundHandler<MessageResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponse msg) {
        PrintUtil.println(msg.getCreateTime(), msg.getNickName(), msg.getMessage());
    }

}
