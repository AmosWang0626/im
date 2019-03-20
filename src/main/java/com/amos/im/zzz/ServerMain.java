package com.amos.im.zzz;

import com.amos.im.common.PacketProtocol;
import com.amos.im.common.ResponseEnum;
import com.amos.im.request.LoginRequest;
import com.amos.im.request.LoginResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * PROJECT: im
 * DESCRIPTION: note
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
                ResponseEnum responseEnum = ResponseEnum.SUCCESS;
                LoginResponse response = new LoginResponse();

                ByteBuf byteBuf = (ByteBuf) msg;
                LoginRequest request = (LoginRequest) PacketProtocol.decode(byteBuf);
                if (request != null && validSuccess(request)) {
                    System.out.println("[服务端] >>> 客户端登录成功!!!");
                    response.setNickname(desensitization(request.getPhoneNo())).setToken("666666666666");
                } else {
                    responseEnum = ResponseEnum.LOGIN_FAIL;
                }

                // 设置登录结果
                response.setResponseEnum(responseEnum);

                ByteBuf encode = PacketProtocol.encode(response);
                ctx.channel().writeAndFlush(encode);
            }
        };
    }

    /**
     * 校验登录信息
     *
     * @param loginRequest LoginRequest
     * @return true: 信息正确
     */
    private static boolean validSuccess(LoginRequest loginRequest) {
        return "18937128861".equals(loginRequest.getPhoneNo())
                && "123456".equals(loginRequest.getPassword());
    }

    /**
     * 手机号脱敏
     *
     * @param phoneNo 手机号
     * @return 脱敏手机号
     */
    private static String desensitization(String phoneNo) {
        if (phoneNo.length() != 11) {
            return phoneNo.substring(0, 4);
        }

        return phoneNo.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
