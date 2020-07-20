package com.amos.im.core.config;

import com.amos.im.common.util.LogUtils;
import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.constant.RedisKeys;
import com.amos.im.core.initializer.NettyServerBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * DESCRIPTION: ImNettyServer
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/3/14
 */
@Slf4j
@Component
public class ImNettyServer implements ApplicationListener<ContextRefreshedEvent> {

    private static ApplicationContext context = null;

    @Resource
    private NettyServerBootstrap nettyServerBootstrap;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        init();

        if (event.getApplicationContext().getParent() == null) {
            context = event.getApplicationContext();

            String startInfo = nettyServerBootstrap.start();
            LogUtils.info(RedisKeys.SERVER_RUN_LOG, startInfo, this.getClass());
        }

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

    public static ApplicationContext getContext() {
        return context;
    }
}
