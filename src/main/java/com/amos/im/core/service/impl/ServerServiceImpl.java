package com.amos.im.core.service.impl;

import com.amos.im.common.protocol.PacketCodec;
import com.amos.im.common.protocol.PacketSplitter;
import com.amos.im.common.util.LogUtils;
import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.config.ImConfig;
import com.amos.im.core.constant.RedisKeys;
import com.amos.im.core.handler.ImHandler;
import com.amos.im.core.handler.LoginRequestHandler;
import com.amos.im.core.service.ServerService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 服务端核心实现
 *
 * @author amos
 * @date 2019/6/2
 */
@Service("serverService")
public class ServerServiceImpl implements ServerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerServiceImpl.class);

    @Resource
    private ImConfig imConfig;

    /**
     * 接受新连接线程，主要负责创建新连接
     */
    private volatile static NioEventLoopGroup BOSS_GROUP;
    /**
     * 读取数据的线程，主要用于读取数据以及业务逻辑处理
     */
    private volatile static NioEventLoopGroup WORK_GROUP;


    @Override
    public String start() {
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

        bind(serverBootstrap, imConfig.getPort());

        String runPort = RedisUtil.get(RedisKeys.SERVER_RUN_PORT);
        if (StringUtils.isNotBlank(runPort)) {
            return "服务端启动成功! 端口号: " + runPort;
        } else {
            return "服务端启动中, 请查看启动日志!";
        }
    }

    @Override
    public List<String> logs() {

        return RedisUtil.lrange(RedisKeys.SERVER_RUN_LOG, 0, -1);
    }

    private void bind(ServerBootstrap serverBootstrap, final Integer startPort) {
        serverBootstrap.bind(startPort).addListener(future -> {
            if (future.isSuccess()) {
                String tempLog = String.format("[服务端启动] >>> 成功! 端口号: %s", startPort);
                RedisUtil.lpush(RedisKeys.SERVER_RUN_LOG, LogUtils.info(tempLog, this.getClass()));

                // 保存服务端启动的端口
                RedisUtil.set(RedisKeys.SERVER_RUN_PORT, String.valueOf(startPort));
                return;
            }

            String tempLog = String.format("[服务端启动] >>> 失败! %s", future.cause().getMessage());
            RedisUtil.lpush(RedisKeys.SERVER_RUN_LOG, LogUtils.error(tempLog, this.getClass()));

            bind(serverBootstrap, startPort + 1);
        });
    }

    private void initGroup() {
        if (BOSS_GROUP == null) {
            BOSS_GROUP = new NioEventLoopGroup();
        }
        if (WORK_GROUP == null) {
            WORK_GROUP = new NioEventLoopGroup();
        }
    }

}
