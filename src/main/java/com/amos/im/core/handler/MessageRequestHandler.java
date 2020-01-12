package com.amos.im.core.handler;

import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.command.request.MessageRequest;
import com.amos.im.core.command.response.MessageResponse;
import com.amos.im.core.vo.UserInfoVO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.MessageFormat;
import java.time.LocalDateTime;

/**
 * DESCRIPTION: 服务端 >>> 转发消息 到 指定客户端
 *
 * @author Daoyuan
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequest> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest messageRequest) {
        UserInfoVO userInfo = AttributeLoginUtil.getUserInfo(messageRequest.getSender());
        System.out.println(MessageFormat.format("[{0}] >>> {1}, {2}",
                userInfo.getUsername(), messageRequest.getCreateTime(), messageRequest.getMessage()));

        // 根据消息中指定的token，发送给对应用户
        Channel channel = AttributeLoginUtil.getChannel(messageRequest.getReceiver());
        System.out.println(this.getClass().getSimpleName() + channel);

        MessageResponse response = new MessageResponse();
        response.setFromToken(messageRequest.getSender()).setUsername(userInfo.getUsername())
                .setMessage(messageRequest.getMessage()).setCreateTime(LocalDateTime.now());

        if (channel != null && AttributeLoginUtil.hasLogin(channel)) {
            channel.writeAndFlush(response);
        } else {
            response.setFromToken("SERVER").setUsername("服务器")
                    .setMessage(MessageFormat.format("[{0}] 未登录, 暂不能收到您的消息!!!", messageRequest.getReceiver()))
                    .setCreateTime(LocalDateTime.now());
            ctx.channel().writeAndFlush(response);
        }
    }

}
