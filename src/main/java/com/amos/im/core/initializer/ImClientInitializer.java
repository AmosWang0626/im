package com.amos.im.core.initializer;

import com.amos.im.common.protocol.PacketCodec;
import com.amos.im.common.protocol.PacketSplitter;
import com.amos.im.core.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * DESCRIPTION: ImClientInitializer
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/3/14
 */
public class ImClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new PacketSplitter());
        ch.pipeline().addLast(PacketCodec.INSTANCE);
        ch.pipeline().addLast(new LoginResponseHandler());
        ch.pipeline().addLast(new AuthHandler());
        ch.pipeline().addLast(new MessageResponseHandler());
        ch.pipeline().addLast(new GroupCreateResponseHandler());
        ch.pipeline().addLast(new GroupJoinResponseHandler());
        ch.pipeline().addLast(new GroupMemberListResponseHandler());
        ch.pipeline().addLast(new GroupQuitResponseHandler());
        ch.pipeline().addLast(new GroupMessageResponseHandler());
    }

}
