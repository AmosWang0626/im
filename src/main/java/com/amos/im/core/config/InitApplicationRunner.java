package com.amos.im.core.config;

import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.constant.RedisKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * PROJECT: Sales
 * DESCRIPTION: 项目初始化
 *
 * @author amos
 * @date 2019/6/2
 */
@Slf4j
@Order(1)
@Configuration
public class InitApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("init ...");
//        RedisUtil.del(RedisKeys.SERVER_RUN_PORT);
        RedisUtil.del(RedisKeys.SERVER_RUN_LOG);
        RedisUtil.del(RedisKeys.CLIENT_RUN_LOG);
        log.info("init finish");
    }

}
