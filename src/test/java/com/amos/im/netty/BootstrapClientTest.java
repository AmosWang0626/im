package com.amos.im.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2018/10/18
 */
public class BootstrapClientTest {

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println(new Date() + "：Client 客户端发送数据……");
                                // 获取一个ByteBuf内存管理器
                                ByteBuf byteBuf = ctx.alloc().buffer();
                                byteBuf.writeBytes("HELLO, AMOS SERVER".getBytes(StandardCharsets.UTF_8));
                                ctx.channel().writeAndFlush(byteBuf);
                            }
                        });
                    }
                });
        // 4.建立连接 [addListener()是一个异步方法]
        bootstrap.connect("localhost", 8081);
    }

}
