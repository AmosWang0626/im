package com.amos.im.core.config;

import com.amos.im.core.handler.NettyServerHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.server.HttpServer;

/**
 * 模块名称: im
 * 模块描述: ImNettyConfig
 *
 * @author amos.wang
 * @date 2020/7/8 10:54
 */
@Configuration
public class ImNettyServerFactory {

    @Bean
    public NettyReactiveWebServerFactory nettyReactiveWebServerFactory() {
        NettyReactiveWebServerFactory webServerFactory = new NettyReactiveWebServerFactory();

        webServerFactory.addServerCustomizers(new ImNettyServerCustomizer());

        return webServerFactory;
    }

    /**
     * 自定义 NettyServer
     */
    private static class ImNettyServerCustomizer implements NettyServerCustomizer {

        @Override
        public HttpServer apply(HttpServer httpServer) {
            // 接受新连接线程，主要负责创建新连接
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            // 读取数据的线程，主要用于读取数据以及业务逻辑处理
            EventLoopGroup workGroup = new NioEventLoopGroup();
            return httpServer.tcpConfiguration(tcpServer -> tcpServer.bootstrap(
                    serverBootstrap -> serverBootstrap
                            .group(bossGroup, workGroup)
                            .channel(NioServerSocketChannel.class)
                            .childOption(ChannelOption.SO_KEEPALIVE, true)
                            .childHandler(new NettyServerHandler())
            ));
        }
    }

}
