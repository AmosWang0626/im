package com.amos.im.controller;

import com.amos.im.request.MessageRequest;
import com.amos.im.request.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Random;

public class MessageServerHandler extends SimpleChannelInboundHandler<MessageRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest msg) throws Exception {
        System.out.println(MessageFormat.format("[服务端接收消息] >>> TIME: [{0}], \t MESSAGE: [{1}]",
                msg.getCreateTime(), msg.getMessage()));

        String callback = new Random().nextBoolean() ? "不听不听我不听" : "啊不然嘞你想怎样";
        MessageResponse response = new MessageResponse().setMessage(callback).setCreateTime(new Date());

        ctx.channel().writeAndFlush(response);
    }

}
