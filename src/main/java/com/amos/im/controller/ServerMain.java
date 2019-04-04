package com.amos.im.controller;

import com.amos.im.common.protocol.PacketCodec;
import com.amos.im.common.protocol.PacketSplitter;
import com.amos.im.controller.handler.ImHandler;
import com.amos.im.controller.handler.LoginRequestHandler;
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
                        ch.pipeline().addLast(PacketCodec.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(ImHandler.INSTANCE);
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
