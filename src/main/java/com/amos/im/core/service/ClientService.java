package com.amos.im.core.service;

import com.amos.im.core.command.request.LoginRequest;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 客户端核心实现
 *
 * @author amos
 * @date 2019/6/2
 */
public interface ClientService {

    /**
     * 客户端启动
     *
     * @param loginRequest 登录信息
     * @return 客户端启动状态
     */
    String start(LoginRequest loginRequest);

    /**
     * 客户端日志
     *
     * @return 日志
     */
    List<String> logs();
}
