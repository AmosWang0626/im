package com.amos.im.core.handler;

import com.amos.im.common.BasePacket;
import com.amos.im.core.command.Command;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * PROJECT: im
 * DESCRIPTION: 简化同级Handler >>>>> ch.pipeline().addLast()
 *
 * @author amos
 * @date 2019/4/4
 */
@ChannelHandler.Sharable
public class ImHandler extends SimpleChannelInboundHandler<BasePacket> {

    public static final ImHandler INSTANCE = new ImHandler();

    private final Map<Byte, SimpleChannelInboundHandler<? extends BasePacket>> handlerMap;

    private ImHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_CREATE_REQUEST, GroupCreateRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_JOIN_REQUEST, GroupJoinRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_LIST_REQUEST, GroupMemberListRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_QUIT_REQUEST, GroupQuitRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BasePacket basePacket) throws Exception {
        if (handlerMap.get(basePacket.getCommand()) != null) {
            handlerMap.get(basePacket.getCommand()).channelRead(ctx, basePacket);
        }
    }
}
