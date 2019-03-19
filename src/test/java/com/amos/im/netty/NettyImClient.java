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
import java.util.concurrent.TimeUnit;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2018/10/18
 */
public class NettyImClient {

    private static final int MAX_RETRY = 5;

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

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println(new Date() + "：Client 客户端接收数据 --> " + byteBuf.toString(StandardCharsets.UTF_8));
                            }
                        });
                    }
                });
        // 4.建立连接 [addListener()是一个异步方法]
        bootstrap.connect("localhost", 8081);
    }

    /**
     * 建立连接
     *
     * @param bootstrap 连接引导
     * @param host      域名或IP
     * @param port      端口
     * @param retry     剩余充实次数
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        // connect(bootstrap, "localhost", 8081, MAX_RETRY);
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else {
                int bout = MAX_RETRY - retry + 1;
                System.err.println("连接失败!重试第 " + bout + " 次...");
                if (retry == 1) {
                    System.out.println("重试次数达上限!");
                    System.exit(0);
                }
                bootstrap.config().group()
                        .schedule(() -> connect(bootstrap, host, port, retry - 1), (1 << bout >> 1), TimeUnit.SECONDS);
            }
        });
    }
}
