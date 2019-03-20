package com.amos.im.controller;

import com.amos.im.common.PacketDecoder;
import com.amos.im.common.PacketEncoder;
import com.amos.im.common.attribute.AttributeUtil;
import com.amos.im.request.MessageRequest;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
                        // pipeline像是可以看作是一条流水线，原始的原料(字节流)进来，经过加工，最后输出
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginClientHandler());
                        ch.pipeline().addLast(new MessageClientHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        bootstrap.connect("", 8080).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("[客户端启动] >>> 连接服务器成功!");
                ChannelFuture channelFuture = (ChannelFuture) future;
                startConsoleThread(channelFuture.channel());
                return;
            }

            System.out.println("[客户端启动] >>> 连接服务器失败! " + future.cause().getMessage());
            System.exit(0);
        });
    }

    private static void startConsoleThread(Channel channel) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("netty-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(
                1, 2, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(() -> {
            while (!Thread.interrupted()) {
                if (AttributeUtil.hasLogin(channel)) {
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();
                    if ("exit".equals(line)) {
                        Thread.currentThread().interrupt();
                        System.out.println("已退出聊天!");
                        return;
                    }

                    MessageRequest request = new MessageRequest();
                    request.setMessage(line).setCreateTime(new Date());
                    channel.writeAndFlush(request);
                }
            }
        });

        singleThreadPool.shutdown();
    }

}
