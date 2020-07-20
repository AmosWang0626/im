package com.amos.im.core.initializer;

import com.amos.im.common.util.LogUtils;
import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.config.ImConfig;
import com.amos.im.core.constant.RedisKeys;
import com.amos.im.core.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * DESCRIPTION: Netty Server 引导类
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/3/15
 */
@Component
public class NettyServerBootstrap {

    @Resource
    private ImConfig imConfig;

    /**
     * NettyServer 引导入口
     */
    private final ServerBootstrap serverBootstrap;


    public NettyServerBootstrap() {
        // 接受新连接线程，主要负责创建新连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 读取数据的线程，主要用于读取数据以及业务逻辑处理
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new NettyServerHandler());
    }


    public String start() {
        String serverRunPort = RedisUtil.get(RedisKeys.SERVER_RUN_PORT);
        if (StringUtils.isNoneBlank(serverRunPort)) {
            return "服务端已启动，端口号: " + serverRunPort;
        }

        bind(serverBootstrap, imConfig.getPort());

        return "服务端启动中, 请查看启动日志!";
    }


    /**
     * 服务端绑定端口启动，如果端口被占用，则递归绑定下一个端口。
     *
     * @param serverBootstrap 启动服务端引导类
     * @param startPort       启动端口
     */
    private void bind(ServerBootstrap serverBootstrap, final Integer startPort) {
        serverBootstrap.bind(startPort).addListener(future -> {
            if (future.isSuccess()) {
                String tempLog = String.format("[服务端启动] >>> 成功! 端口号: %s", startPort);
                LogUtils.info(RedisKeys.SERVER_RUN_LOG, tempLog, this.getClass());

                // 保存服务端启动的端口
                RedisUtil.set(RedisKeys.SERVER_RUN_PORT, String.valueOf(startPort));
                return;
            }

            String tempLog = String.format("[服务端启动] >>> 失败! %s", future.cause().getMessage());
            LogUtils.error(RedisKeys.SERVER_RUN_LOG, tempLog, this.getClass());

            bind(serverBootstrap, startPort + 1);
        });
    }

}
