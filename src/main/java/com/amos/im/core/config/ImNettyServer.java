package com.amos.im.core.config;

import com.amos.im.common.util.LogUtils;
import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.constant.RedisKeys;
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
//@Component
public class ImNettyServer implements ApplicationListener<ContextRefreshedEvent> {

    private static ApplicationContext context = null;

    @Resource
    private NettyReactiveWebServerFactory nettyReactiveWebServerFactory;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        init();

        context = event.getApplicationContext();

        // 启动成功获取启动的端口
        int port = nettyReactiveWebServerFactory.getPort();

        // 记录启动日志
        String tempLog = String.format("[服务端启动] >>> 成功! 端口号: %s", port);
        LogUtils.info(RedisKeys.SERVER_RUN_LOG, tempLog, getClass());

        // 保存服务端启动的端口
        RedisUtil.set(RedisKeys.SERVER_RUN_PORT, String.valueOf(port));
    }

    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * 初始化项目配置
     */
    private void init() {
        long start = System.currentTimeMillis();
        RedisUtil.del(RedisKeys.SERVER_RUN_PORT);
        RedisUtil.del(RedisKeys.SERVER_RUN_LOG);
        log.info("初始化项目配置完成, 耗时 {}毫秒!", (System.currentTimeMillis() - start));
    }

}
