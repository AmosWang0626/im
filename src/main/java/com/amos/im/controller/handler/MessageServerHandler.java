package com.amos.im.controller.handler;

import com.amos.im.common.attribute.AttributeUtil;
import com.amos.im.controller.request.MessageRequest;
import com.amos.im.controller.request.MessageResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Random;

/**
 * DESCRIPTION: 服务端 >>> 转发消息 到 指定客户端
 *
 * @author Daoyuan
 */
public class MessageServerHandler extends SimpleChannelInboundHandler<MessageRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest messageRequest) throws Exception {
        System.out.println(MessageFormat.format("[服务端接收消息] >>> TIME: [{0}], \t MESSAGE: [{1}]",
                messageRequest.getCreateTime(), messageRequest.getMessage()));

        // 根据消息中指定的token，发送给对应用户
        Channel channel = AttributeUtil.getChannel(messageRequest.getToToken());

        MessageResponse response = new MessageResponse();
        response.setCreateTime(new Date()).setFromToken(AttributeUtil.getToken(ctx.channel()))
                .setMessage(new Random().nextBoolean() ? "不听不听我不听" : "啊不然嘞你想怎样");

        if (channel != null && AttributeUtil.hasLogin(channel)) {
            channel.writeAndFlush(response);
        } else {
            System.out.println(MessageFormat.format(
                    "[客户端{0}] >>> [{1}] 不在线, 发送失败!", channel, messageRequest.getToToken()));
        }
    }

}
