package com.amos.im.controller.handler;

import com.amos.im.common.util.PrintUtil;
import com.amos.im.controller.request.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Daoyuan
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponse> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, MessageResponse msg) {
        PrintUtil.message(msg.getCreateTime(), msg.getNickName(), msg.getMessage());
    }

}
