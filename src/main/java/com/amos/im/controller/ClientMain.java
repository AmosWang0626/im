package com.amos.im.controller;

import com.amos.im.common.attribute.AttributeUtil;
import com.amos.im.common.protocol.PacketDecoder;
import com.amos.im.common.protocol.PacketEncoder;
import com.amos.im.common.protocol.PacketSplitter;
import com.amos.im.common.util.PrintUtil;
import com.amos.im.controller.handler.AuthHandler;
import com.amos.im.controller.handler.LoginClientHandler;
import com.amos.im.controller.handler.MessageClientHandler;
import com.amos.im.controller.request.LoginRequest;
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
                        ch.pipeline().addLast(new AuthHandler());
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
            Scanner sc = new Scanner(System.in);
            if (AttributeUtil.hasLogin(channel)) {
                String token = sc.next();
                exit(token);
                String message = sc.nextLine();

                Date sendTime = new Date();
                MessageRequest request = new MessageRequest();
                request.setToToken(token).setMessage(message).setCreateTime(new Date());
                channel.writeAndFlush(request);

                PrintUtil.println(sendTime, "我", message);
            } else {
                System.out.println("请输入[用户名 密码]登录: ");
                String phoneNo = sc.next();
                exit(phoneNo);
                String password = sc.next();

                LoginRequest loginRequest = new LoginRequest().setPhoneNo(phoneNo).setPassword(password);
                channel.writeAndFlush(loginRequest);
            }
            waitLogin();
        }
    }

    private static void waitLogin() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果输入exit则退出
     *
     * @param txt 输入文本
     */
    private static void exit(String txt) {
        if (Thread.currentThread().getStackTrace()[1].getMethodName().equals(txt)) {
            Thread.currentThread().interrupt();
            System.out.println("已退出聊天!");
            System.exit(0);
        }
    }

}