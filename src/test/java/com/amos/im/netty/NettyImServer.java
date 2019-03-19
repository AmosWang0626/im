package com.amos.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Channel：通道
 * Buffer：缓冲区
 * Selector：选择器
 * <p>
 * 关系: 数据 Channel <<===>> Buffer
 * 关系: Selector 监听 Channel
 *
 * @author Daoyuan
 * @date 2018/10/18
 */
public class NettyImServer {

    public static void main(String[] args) {
        // bossGroup表示监听端口，accept 新连接的线程组;
        // workerGroup表示处理每一条连接的数据读写的线程组.
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        // 引导我们进行服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                // IO 模型(同步阻塞IO: OioServerSocketChannel.class)
                // IO 模型(同步非阻塞IO: NioServerSocketChannel.class)
                .channel(NioServerSocketChannel.class)
                // childHandler()用于指定处理新连接数据的读写处理逻辑
                // handler()用于指定在服务端启动过程中的一些逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println(new Date() + "：Server 服务器接收数据 --> " + byteBuf.toString(StandardCharsets.UTF_8));

                                System.out.println(new Date() + "：Server 服务器返回数据……");
                                byteBuf = ctx.alloc().buffer();
                                byteBuf.writeBytes("HELLO, CLIENT".getBytes(StandardCharsets.UTF_8));
                                ctx.channel().writeAndFlush(byteBuf);
                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                System.out.println(new Date() + "：有新的 Client 客户端注册进来，通知一下吧……");
                                ByteBuf byteBuf = ctx.alloc().buffer();
                                byteBuf.writeBytes("欢迎加入 AMOS 服务器!!! ".getBytes(StandardCharsets.UTF_8));
                                ctx.channel().writeAndFlush(byteBuf);
                            }
                        });
                    }
                });
        serverBootstrap.bind(8081);
    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        // bind(serverBootstrap, 8081);
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败! 尝试其他端口开始...");
                bind(serverBootstrap, port + 1);
            }
        });
    }

}
