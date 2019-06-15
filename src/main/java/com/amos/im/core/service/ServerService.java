package com.amos.im.core.service;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 服务端核心实现
 *
 * @author amos
 * @date 2019/6/2
 */
public interface ServerService {

    /**
     * 服务端启动
     *
     * @return 服务端启动状态
     */
    String start();

    /**
     * 服务端日志
     *
     * @return 日志
     */
    List<String> logs();

}
