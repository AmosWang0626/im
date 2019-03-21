package com.amos.im.controller.handler;

import com.amos.im.common.attribute.AttributeUtil;
import com.amos.im.controller.dto.LoginVO;
import com.amos.im.controller.request.MessageRequest;
import com.amos.im.controller.request.MessageResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.MessageFormat;
import java.util.Date;

/**
 * DESCRIPTION: 服务端 >>> 转发消息 到 指定客户端
 *
 * @author Daoyuan
 */
public class MessageServerHandler extends SimpleChannelInboundHandler<MessageRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest messageRequest) {
        LoginVO sendClient = AttributeUtil.getToken(ctx.channel());
        System.out.println(MessageFormat.format("[{0}] >>> {1}, {2}",
                sendClient.getNickname(), messageRequest.getCreateTime(), messageRequest.getMessage()));

        // 根据消息中指定的token，发送给对应用户
        Channel channel = AttributeUtil.getChannel(messageRequest.getToToken());

        MessageResponse response = new MessageResponse();
        response.setCreateTime(new Date())
                .setFromToken(sendClient.getToken())
                .setNickName(sendClient.getNickname())
                .setMessage(messageRequest.getMessage());

        if (channel != null && AttributeUtil.hasLogin(channel)) {
            channel.writeAndFlush(response);
        } else {
            System.out.println(MessageFormat.format(
                    "[客户端{0}] >>> [{1}] 不在线, 发送失败!", channel, messageRequest.getToToken()));
        }
    }

}
