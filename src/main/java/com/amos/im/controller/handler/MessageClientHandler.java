package com.amos.im.controller.handler;

import com.amos.im.controller.request.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.MessageFormat;

/**
 * @author Daoyuan
 */
public class MessageClientHandler extends SimpleChannelInboundHandler<MessageResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponse msg) throws Exception {
        System.out.println(MessageFormat.format("[客户端回复消息] >>> TIME: [{0}], \t MESSAGE: [{1}]",
                msg.getCreateTime(), msg.getMessage()));
    }

}
