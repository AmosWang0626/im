package com.amos.im.controller;

import com.amos.im.common.attribute.AttributeUtil;
import com.amos.im.common.protocol.PacketDecoder;
import com.amos.im.common.protocol.PacketEncoder;
import com.amos.im.common.protocol.PacketSplitter;
import com.amos.im.controller.handler.LoginClientHandler;
import com.amos.im.controller.handler.MessageClientHandler;
import com.amos.im.controller.request.MessageRequest;
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

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * PROJECT: im
 * DESCRIPTION: 客户端
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public class ClientMain {

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
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginClientHandler());
                        ch.pipeline().addLast(new MessageClientHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        bootstrap.connect("", 8080).addListener(futureListener());
    }

    private static GenericFutureListener<Future<? super Void>> futureListener() {
        return future -> {
            if (future.isSuccess()) {
                System.out.println("[客户端启动] >>> 连接服务器成功!");
                ChannelFuture channelFuture = (ChannelFuture) future;
                // 启动线程池实现客户端与服务端交互
                startThreadPool(channelFuture.channel());
                return;
            }

            System.out.println("[客户端启动] >>> 连接服务器失败! " + future.cause().getMessage());
            System.exit(0);
        };
    }

    private static void startThreadPool(Channel channel) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("netty-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(
                1, 2, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(() -> console(channel));

        singleThreadPool.shutdown();
    }

    /**
     * 与服务端交互
     *
     * @param channel 通道
     */
    private static void console(Channel channel) {
        while (!Thread.interrupted()) {
            if (AttributeUtil.hasLogin(channel)) {
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                if ("exit".equals(line)) {
                    Thread.currentThread().interrupt();
                    System.out.println("已退出聊天!");
                    System.exit(0);
                }

                MessageRequest request = new MessageRequest();
                request.setMessage(line).setCreateTime(new Date());
                channel.writeAndFlush(request);
            }
        }
    }

}
