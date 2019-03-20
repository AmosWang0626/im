package com.amos.im.zzz;

import com.amos.im.common.BasePacket;
import com.amos.im.common.PacketProtocol;
import com.amos.im.common.ResponseEnum;
import com.amos.im.request.LoginRequest;
import com.amos.im.request.LoginResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * PROJECT: im
 * DESCRIPTION: note
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
                return;
            }

            System.out.println("[客户端启动] >>> 连接服务器失败! " + future.cause().getMessage());
            System.exit(0);
        });
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
                LoginRequest loginRequest = new LoginRequest()
                        .setPhoneNo("18937128861").setPassword("123456");
                ByteBuf encode = PacketProtocol.encode(loginRequest);
                ctx.channel().writeAndFlush(encode);
            }

            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                ByteBuf byteBuf = (ByteBuf) msg;
                BasePacket basePacket = PacketProtocol.decode(byteBuf);
                if (basePacket instanceof LoginResponse) {
                    LoginResponse loginResponse = (LoginResponse) basePacket;
                    if (ResponseEnum.SUCCESS.equals(loginResponse.getResponseEnum())) {
                        System.out.println("[客户端] >>> " + loginResponse.getNickname() + " 登录成功!!!");
                        return;
                    }
                    System.out.println("[客户端] >>> 登录失败!!! " + loginResponse.getResponseEnum().getMsg());
                }
            }
        };
    }

}
