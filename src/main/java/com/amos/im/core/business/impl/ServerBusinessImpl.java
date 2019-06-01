package com.amos.im.core.business.impl;

import com.amos.im.common.protocol.PacketCodec;
import com.amos.im.common.protocol.PacketSplitter;
import com.amos.im.core.business.ServerBusiness;
import com.amos.im.core.handler.ImHandler;
import com.amos.im.core.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * PROJECT: Sales
 * DESCRIPTION: 服务端核心业务
 *
 * @author amos
 * @date 2019/6/1
 */
@Service
public class ServerBusinessImpl implements ServerBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerBusinessImpl.class);

    private volatile static NioEventLoopGroup BOSS_GROUP;
    private volatile static NioEventLoopGroup WORK_GROUP;


    @Override
    public void init() {
        initGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(BOSS_GROUP, WORK_GROUP)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new PacketSplitter());
                        ch.pipeline().addLast(PacketCodec.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(ImHandler.INSTANCE);
                    }
                });

        serverBootstrap.bind(8080).addListener(futureListener());
    }

    private void initGroup() {
        if (BOSS_GROUP == null) {
            BOSS_GROUP = new NioEventLoopGroup();
        }
        if (WORK_GROUP == null) {
            WORK_GROUP = new NioEventLoopGroup();
        }
    }

    private GenericFutureListener<Future<? super Void>> futureListener() {
        return future -> {
            if (future.isSuccess()) {
                LOGGER.info("[服务端启动] >>> 成功!");
                return;
            }

            LOGGER.info("[服务端启动] >>> 失败! " + future.cause().getMessage());
            System.exit(0);
        };
    }

}
