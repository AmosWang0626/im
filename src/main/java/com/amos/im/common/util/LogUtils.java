package com.amos.im.common.util;

import com.amos.im.core.constant.RedisKeys;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * PROJECT: Sales
 * DESCRIPTION: 日志工具类
 *
 * @author amos
 * @date 2019/6/2
 */
@Component
public class LogUtils {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final String TEMPLATE_INFO = "[INFO]( %s ): %s";
    private static final String TEMPLATE_ERROR = "[ERROR]( %s ): %s";


    /**
     * 服务端日志
     */
    public void serverInfo(String log, Class clazz) {
        baseInfo(RedisKeys.SERVER_RUN_LOG, log, clazz);
    }

    public void serverError(String log, Class clazz) {
        baseError(RedisKeys.SERVER_RUN_LOG, log, clazz);
    }

    /**
     * 客户端日志
     */
    public void clientInfo(String log, Class clazz) {
        baseInfo(RedisKeys.CLIENT_RUN_LOG, log, clazz);
    }

    public void clientError(String log, Class clazz) {
        baseError(RedisKeys.CLIENT_RUN_LOG, log, clazz);
    }


    /**
     * info logs
     *
     * @param channel RedisKeys.SERVER_RUN_LOG || RedisKeys.CLIENT_RUN_LOG
     * @param log     log content
     * @param clazz   print log clazz
     */
    private void baseInfo(String channel, String log, Class clazz) {
        LoggerFactory.getLogger(clazz).info(log);

        if (RedisKeys.CLIENT_RUN_LOG.equals(channel)) {
            simpMessagingTemplate.convertAndSend("/client/message", log);
            RedisUtil.lpush(channel, String.format(TEMPLATE_INFO, DateUtil.getNowStr(), log));
            return;
        }

        simpMessagingTemplate.convertAndSend("/server/message", log);
        RedisUtil.lpush(RedisKeys.SERVER_RUN_LOG, String.format(TEMPLATE_INFO, DateUtil.getNowStr(), log));
    }

    /**
     * error logs
     *
     * @param channel RedisKeys.SERVER_RUN_LOG || RedisKeys.CLIENT_RUN_LOG
     * @param log     log content
     * @param clazz   print log clazz
     */
    private void baseError(String channel, String log, Class clazz) {
        LoggerFactory.getLogger(clazz).error(log);

        if (RedisKeys.CLIENT_RUN_LOG.equals(channel)) {
            simpMessagingTemplate.convertAndSend("/client/message", log);
            RedisUtil.lpush(RedisKeys.CLIENT_RUN_LOG, String.format(TEMPLATE_ERROR, DateUtil.getNowStr(), log));
            return;
        }

        simpMessagingTemplate.convertAndSend("/server/message", log);
        RedisUtil.lpush(RedisKeys.SERVER_RUN_LOG, String.format(TEMPLATE_ERROR, DateUtil.getNowStr(), log));
    }

}
