package com.amos.im.core.business;

import com.amos.im.core.request.LoginRequest;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 客户端核心业务
 *
 * @author amos
 * @date 2019/6/1
 */
public interface ClientBusiness {

    /**
     * 登录
     *
     * @param loginRequest 登录表单
     * @return 登录结果
     */
    String login(LoginRequest loginRequest);

    /**
     * 服务端日志
     *
     * @return 日志
     */
    List<String> logs();

}
