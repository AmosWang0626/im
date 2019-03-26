package com.amos.im.controller.handler;

import com.amos.im.common.attribute.AttributeLoginUtil;
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
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest messageRequest) {
        LoginVO sendClient = AttributeLoginUtil.getLoginInfo(ctx.channel());
        System.out.println(MessageFormat.format("[{0}] >>> {1}, {2}",
                sendClient.getNickname(), messageRequest.getCreateTime(), messageRequest.getMessage()));

        // 根据消息中指定的token，发送给对应用户
        Channel channel = AttributeLoginUtil.getChannel(messageRequest.getToToken());

        MessageResponse response = new MessageResponse();
        response.setCreateTime(new Date())
                .setFromToken(sendClient.getToken())
                .setNickName(sendClient.getNickname())
                .setMessage(messageRequest.getMessage());

        if (channel != null && AttributeLoginUtil.hasLogin(channel)) {
            channel.writeAndFlush(response);
        } else {
            response.setCreateTime(new Date())
                    .setFromToken("SERVER")
                    .setNickName("服务器")
                    .setMessage(MessageFormat.format("[{0}] 未登录, 暂不能收到您的消息!!!", messageRequest.getToToken()));
            ctx.channel().writeAndFlush(response);
        }
    }

}
