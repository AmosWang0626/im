package com.amos.im.core.business;

import com.amos.im.core.command.request.LoginRequest;
import com.amos.im.core.vo.UserInfoVO;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 客户端核心业务
 *
 * @author amos
 * @date 2019/6/1
 */
public interface LoginBusiness {

    /**
     * 登录
     *
     * @param loginRequest 登录表单
     * @return 登录结果
     */
    String login(LoginRequest loginRequest);

    /**
     * 登录日志
     *
     * @return 日志
     */
    List<String> logs();

    /**
     * 已登录用户列表
     *
     * @return list
     */
    List<UserInfoVO> list();
}
