package com.amos.im.common.util;

import com.amos.im.core.constant.RedisKeys;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * PROJECT: Sales
 * DESCRIPTION: 日志工具类
 *
 * @author amos
 * @date 2019/6/2
 */
@Configuration
public class LogUtils {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    private static LogUtils logUtils;

    private static final String TEMPLATE_INFO = "[INFO]( %s ): %s";
    private static final String TEMPLATE_ERROR = "[ERROR]( %s ): %s";

    @PostConstruct
    public void init() {
        logUtils = this;
    }

    public static void info(String channel, String log, Class<?> clazz) {
        if (logUtils == null) {
            LoggerFactory.getLogger(clazz).info(log);
            return;
        }
        logUtils.baseInfo(channel, log, clazz);
    }

    public static void error(String channel, String log, Class<?> clazz) {
        if (logUtils == null) {
            LoggerFactory.getLogger(clazz).error(log);
            return;
        }
        logUtils.baseError(channel, log, clazz);
    }


    /**
     * info logs
     *
     * @param channel RedisKeys
     * @param log     log content
     * @param clazz   print log clazz
     * @see RedisKeys
     */
    private void baseInfo(String channel, String log, Class<?> clazz) {
        LoggerFactory.getLogger(clazz).info(log);
        String infoLog = String.format(TEMPLATE_INFO, DateUtil.getNowStr(), log);

        simpMessagingTemplate.convertAndSend("/server/logs", infoLog);
        RedisUtil.lpush(RedisKeys.SERVER_RUN_LOG, infoLog);
    }

    /**
     * error logs
     *
     * @param channel RedisKeys.SERVER_RUN_LOG || RedisKeys.CLIENT_RUN_LOG
     * @param log     log content
     * @param clazz   print log clazz
     */
    private void baseError(String channel, String log, Class<?> clazz) {
        LoggerFactory.getLogger(clazz).error(log);
        String errorLog = String.format(TEMPLATE_ERROR, DateUtil.getNowStr(), log);

        simpMessagingTemplate.convertAndSend("/server/logs", errorLog);
        RedisUtil.lpush(RedisKeys.SERVER_RUN_LOG, errorLog);
    }

}
