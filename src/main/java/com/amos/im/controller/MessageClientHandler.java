package com.amos.im.controller;

import com.amos.im.request.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.MessageFormat;

public class MessageClientHandler extends SimpleChannelInboundHandler<MessageResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponse msg) throws Exception {
        System.out.println(MessageFormat.format("[服务端回复消息] >>> TIME: [{0}], \t MESSAGE: [{1}]",
                msg.getCreateTime(), msg.getMessage()));
    }

}
