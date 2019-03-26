package com.amos.im.controller;

import com.amos.im.common.protocol.PacketDecoder;
import com.amos.im.common.protocol.PacketEncoder;
import com.amos.im.common.protocol.PacketSplitter;
import com.amos.im.controller.handler.CreateGroupServerHandler;
import com.amos.im.controller.handler.LoginServerHandler;
import com.amos.im.controller.handler.MessageServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * PROJECT: im
 * DESCRIPTION: 服务端
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public class ServerMain {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new PacketSplitter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginServerHandler());
                        ch.pipeline().addLast(new MessageServerHandler());
                        ch.pipeline().addLast(new CreateGroupServerHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        serverBootstrap.bind(8080).addListener(futureListener());
    }

    private static GenericFutureListener<Future<? super Void>> futureListener() {
        return future -> {
            if (future.isSuccess()) {
                System.out.println("[服务端启动] >>> 成功!");
                return;
            }

            System.out.println("[服务端启动] >>> 失败! " + future.cause().getMessage());
            System.exit(0);
        };
    }

}
