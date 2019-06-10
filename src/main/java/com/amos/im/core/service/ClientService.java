package com.amos.im.core.service;

import com.amos.im.core.command.request.LoginRequest;

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
     */
    void start(LoginRequest loginRequest);

}
