package com.amos.im.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * DESCRIPTION: ImServerBootstrap
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/3/14
 */
@Slf4j
@Component
public class ImNettyServer implements ApplicationListener<ContextRefreshedEvent> {

    private static ApplicationContext context = null;

    @Resource
    private NettyReactiveWebServerFactory nettyReactiveWebServerFactory;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        context = event.getApplicationContext();

        // 启动成功获取启动的端口
        int port = nettyReactiveWebServerFactory.getPort();

        // 记录启动日志
        log.info("[服务端启动] >>> 成功! 端口号: {}", port);
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
