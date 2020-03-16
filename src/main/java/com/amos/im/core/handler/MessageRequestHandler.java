package com.amos.im.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.amos.im.core.command.request.MessageRequest;
import com.amos.im.core.command.response.MessageResponse;
import com.amos.im.core.pojo.vo.LoginInfoVO;
import com.amos.im.core.session.ServerSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

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
        LoginInfoVO userInfo = ServerSession.getLoginInfo(messageRequest.getSender());
        ServerSession.reBindToken(ctx.channel(), messageRequest.getSender());

        // 根据消息中指定的token，发送给对应用户
        Channel channel = ServerSession.getChannel(messageRequest.getReceiver());

        MessageResponse response = new MessageResponse();
        response.setUsername(userInfo.getUsername())
                .setReceiver(messageRequest.getReceiver())
                .setMessage(messageRequest.getMessage())
                .setCreateTime(LocalDateTime.now())
                .setSender(messageRequest.getSender());

        if (channel != null && ServerSession.hasLogin(channel)) {
            channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(response)));
        } else {
            response.setUsername("服务器")
                    .setMessage(MessageFormat.format("[{0}] 未登录, 暂不能收到您的消息!!!", messageRequest.getReceiver()))
                    .setCreateTime(LocalDateTime.now())
                    .setSender("SERVER");
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(response)));
        }
    }

}
