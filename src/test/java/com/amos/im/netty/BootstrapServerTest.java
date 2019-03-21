package com.amos.im.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
public class BootstrapServerTest {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(inboundAdapterA());
                        ch.pipeline().addLast(inboundAdapterB());
                        ch.pipeline().addLast(inboundAdapterC());

                        ch.pipeline().addLast(outboundAdapterA());
                        ch.pipeline().addLast(outboundAdapterB());
                        ch.pipeline().addLast(outboundAdapterC());

                        ch.pipeline().write("AAA");
                    }
                });
        serverBootstrap.bind(8081);
    }

    private static ChannelHandlerAdapter inboundAdapterA() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                System.out.println("InboundAdapter >>>>>> AAA");
                super.channelRead(ctx, msg);
            }
        };
    }

    private static ChannelHandlerAdapter inboundAdapterB() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ctx.fireChannelRead(msg);
                System.out.println("InboundAdapter >>>>>> BBB");
                // super.channelRead(ctx, msg);
            }

        };
    }

    private static ChannelHandlerAdapter inboundAdapterC() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                System.out.println("InboundAdapter >>>>>> CCC");
                super.channelRead(ctx, msg);
            }
        };
    }

    private static ChannelHandlerAdapter outboundAdapterA() {
        return new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                System.out.println("OutboundAdapter >>>>>> AAA");
                super.write(ctx, msg, promise);
            }
        };
    }

    private static ChannelHandlerAdapter outboundAdapterB() {
        return new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                System.out.println("OutboundAdapter >>>>>> BBB");
                super.write(ctx, msg, promise);
            }
        };
    }

    private static ChannelHandlerAdapter outboundAdapterC() {
        return new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                ctx.write(msg, promise);
                System.out.println("OutboundAdapter >>>>>> CCC");
                // super.write(ctx, msg, promise);
            }
        };
    }

}
