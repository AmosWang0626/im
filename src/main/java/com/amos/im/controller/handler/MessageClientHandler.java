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
        System.out.println(MessageFormat.format("\n\t{0}\n{1}:  {2}",
                msg.getCreateTime(), msg.getNickName(), msg.getMessage()));
    }

}
