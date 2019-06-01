package com.amos.im.core.business.impl;

import com.amos.im.common.protocol.PacketCodec;
import com.amos.im.common.protocol.PacketSplitter;
import com.amos.im.core.business.ClientBusiness;
import com.amos.im.core.handler.*;
import com.amos.im.web.console.ConsoleManager;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * PROJECT: Sales
 * DESCRIPTION: 客户端核心业务
 *
 * @author amos
 * @date 2019/6/1
 */
@Service
public class ClientBusinessImpl implements ClientBusiness {

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
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
                });

        bootstrap.connect("", 8080).addListener(futureListener());
    }

    private static GenericFutureListener<Future<? super Void>> futureListener() {
        return future -> {
            if (future.isSuccess()) {
                System.out.println("[客户端启动] >>> 连接服务器成功!");
                ChannelFuture channelFuture = (ChannelFuture) future;
                // 控制台
                startThreadPool(channelFuture.channel());
                return;
            }

            System.out.println("[客户端启动] >>> 连接服务器失败! " + future.cause().getMessage());
            System.exit(0);
        };
    }

    /**
     * 线程启动控制台,实现客户端与服务端交互
     *
     * @param channel channel
     */
    private static void startThreadPool(Channel channel) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("netty-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(
                1, 2, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(() -> {
            while (!Thread.interrupted()) {
                // 客户端控制台
                ConsoleManager.newInstance().exec(channel);
            }
        });

        singleThreadPool.shutdown();
    }

    @Override
    public void init() {

    }

}