package com.amos.im.controller;

import com.amos.im.common.BasePacket;
import com.amos.im.common.GeneralCode;
import com.amos.im.common.PacketProtocol;
import com.amos.im.common.constant.ImConstant;
import com.amos.im.request.LoginRequest;
import com.amos.im.request.LoginResponse;
import com.amos.im.request.MessageRequest;
import com.amos.im.request.MessageResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Random;

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
                        ch.pipeline().addLast(serverHandler());
                    }
                });
        serverBootstrap.bind(8080).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("[服务端启动] >>> 成功!");
                return;
            }

            System.out.println("[服务端启动] >>> 失败! " + future.cause().getMessage());
            System.exit(0);
        });
    }

    private static ChannelHandler serverHandler() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelActive(ChannelHandlerContext ctx) {
                System.out.println("[服务端] >>> 叮咚, 有新客户端加入哟!");
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                ByteBuf byteBuf = (ByteBuf) msg;
                BasePacket basePacket = PacketProtocol.getInstance().decode(byteBuf);
                if (basePacket instanceof LoginRequest) {
                    loginBusiness((LoginRequest) basePacket, ctx.channel());
                } else if (basePacket instanceof MessageRequest) {
                    messageBusiness((MessageRequest) basePacket, ctx.channel());
                }

            }
        };
    }

    private static void messageBusiness(MessageRequest request, Channel channel) {
        System.out.println(MessageFormat.format("[服务端接收消息] >>> TIME: [{0}], \t MESSAGE: [{1}]",
                request.getCreateTime(), request.getMessage()));

        String callback = new Random().nextBoolean() ? "不听不听我不听" : "啊不然嘞你想怎样";
        MessageResponse response = new MessageResponse().setMessage(callback).setCreateTime(new Date());
        ByteBuf encode = PacketProtocol.getInstance().encode(response);
        channel.writeAndFlush(encode);
    }

    private static void loginBusiness(LoginRequest request, Channel channel) {
        GeneralCode generalCode = GeneralCode.SUCCESS;
        LoginResponse response = new LoginResponse();
        if (validSuccess(request)) {
            System.out.println("[服务端] >>> 客户端登录成功!!!");
            response.setNickname(desensitization(request.getPhoneNo())).setToken(String.valueOf(System.currentTimeMillis()));
        } else {
            System.out.println("[服务端] >>> 客户端登录失败!!!");
            generalCode = GeneralCode.LOGIN_FAIL;
        }

        // 设置登录结果
        response.setGeneralCode(generalCode);

        ByteBuf encode = PacketProtocol.getInstance().encode(response);
        channel.writeAndFlush(encode);
    }

    /**
     * 校验登录信息
     *
     * @param loginRequest LoginRequest
     * @return true: 信息正确
     */
    private static boolean validSuccess(LoginRequest loginRequest) {
        return "18937128888".equals(loginRequest.getPhoneNo())
                && "123456".equals(loginRequest.getPassword());
    }

    /**
     * 手机号脱敏
     *
     * @param phoneNo 手机号
     * @return 脱敏手机号
     */
    private static String desensitization(String phoneNo) {
        if (phoneNo.length() != ImConstant.PHONE_NO_LENGTH) {
            return phoneNo.substring(0, 4);
        }

        return phoneNo.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
