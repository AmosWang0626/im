package com.amos.im.core.handler;

import com.amos.im.common.util.PrintUtil;
import com.amos.im.core.command.response.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Daoyuan
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponse msg) {
        PrintUtil.message(msg.getCreateTime(), msg.getNickName(), msg.getMessage());
    }

}
