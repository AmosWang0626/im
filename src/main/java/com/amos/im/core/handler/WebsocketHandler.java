package com.amos.im.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import com.amos.im.core.command.request.LoginRequest;
import com.amos.im.core.command.request.MessageRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * DESCRIPTION: 将前端 Websocket 发来的内容解析，交由后边的 Handler 继续处理
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/3/14
 */
public class WebsocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String message = msg.text();

        System.out.println("[Client]: " + message);

        JSONObject packet = JSONObject.parseObject(message);
        Byte command = packet.getByte("command");
        BasePacket next = null;
        if (Command.LOGIN_REQUEST == command) {
            next = JSONObject.parseObject(message, LoginRequest.class);
        } else if (Command.MESSAGE_REQUEST == command) {
            next = JSONObject.parseObject(message, MessageRequest.class);
        }

        // 继续下一个执行
        ctx.fireChannelRead(next);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
        System.out.printf("客户端已建立连接，客户端ID [%s]，在线人数 [%d人]\n", ctx.channel().id().asShortText(), users.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        users.remove(ctx.channel());
        System.out.printf("客户端连接已关闭，客户端ID [%s]，在线人数 [%d人]\n", ctx.channel().id().asShortText(), users.size());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}
