package com.amos.im.controller;

import com.amos.im.common.BasePacket;
import com.amos.im.common.GeneralCode;
import com.amos.im.common.PacketProtocol;
import com.amos.im.common.attribute.AttributeUtil;
import com.amos.im.request.LoginRequest;
import com.amos.im.request.LoginResponse;
import com.amos.im.request.MessageRequest;
import com.amos.im.request.MessageResponse;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.text.MessageFormat;
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
                        ch.pipeline().addLast(clientHandler());
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
                    }
                    MessageRequest request = new MessageRequest();
                    request.setMessage(line).setCreateTime(new Date());
                    ByteBuf encode = PacketProtocol.getInstance().encode(request);
                    channel.writeAndFlush(encode);
                }
            }
        });

        singleThreadPool.shutdown();
    }

    /**
     * 初始化客户端handler
     *
     * @return ChannelHandler
     */
    private static ChannelHandler clientHandler() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelActive(ChannelHandlerContext ctx) {
                System.out.println("[客户端] >>> 开始登录...");
                LoginRequest loginRequest = new LoginRequest().setPhoneNo("18937128888").setPassword("123456");
                ByteBuf encode = PacketProtocol.getInstance().encode(loginRequest);
                ctx.channel().writeAndFlush(encode);
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                ByteBuf byteBuf = (ByteBuf) msg;
                BasePacket basePacket = PacketProtocol.getInstance().decode(byteBuf);
                if (basePacket instanceof LoginResponse) {
                    loginBusiness(ctx.channel(), (LoginResponse) basePacket);
                } else if (basePacket instanceof MessageResponse) {
                    messageBusiness(ctx.channel(), (MessageResponse) basePacket);
                }
            }
        };
    }

    private static void messageBusiness(Channel channel, MessageResponse response) {
        System.out.println(MessageFormat.format("[服务端回复消息] >>> TIME: [{0}], \t MESSAGE: [{1}]",
                response.getCreateTime(), response.getMessage()));
    }

    private static void loginBusiness(Channel channel, LoginResponse response) {
        if (GeneralCode.SUCCESS.equals(response.getGeneralCode())) {
            System.out.println("[客户端] >>> " + response.getNickname() + " 登录成功, 可以聊天了!!!");
            // 设置登录状态为 true || token
            AttributeUtil.maskLogin(channel);
            AttributeUtil.maskToken(channel, String.valueOf(System.currentTimeMillis()));
        } else {
            System.out.println("[客户端] >>> 登录失败!!! " + response.getGeneralCode().getMsg());
        }
    }

}
