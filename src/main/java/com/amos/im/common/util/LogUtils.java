package com.amos.im.common.util;

import org.slf4j.LoggerFactory;

/**
 * PROJECT: Sales
 * DESCRIPTION: 日志工具类
 *
 * @author amos
 * @date 2019/6/2
 */
public class LogUtils {

    private static final String TEMPLATE_INFO = "[INFO]( %s ): %s";
    private static final String TEMPLATE_ERROR = "[ERROR]( %s ): %s";

    public static String info(String log, Class clazz) {
        LoggerFactory.getLogger(clazz).info(log);
        return String.format(TEMPLATE_INFO, DateUtil.getNowStr(), log);
    }

    public static String error(String log, Class clazz) {
        LoggerFactory.getLogger(clazz).info(log);
        return String.format(TEMPLATE_ERROR, DateUtil.getNowStr(), log);
    }

}
