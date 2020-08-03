package com.amos.im.core.business;

import com.amos.im.core.command.request.LoginRequest;
import com.amos.im.core.command.response.LoginResponse;
import com.amos.im.core.pojo.vo.LoginInfoVO;

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
    LoginResponse login(LoginRequest loginRequest);
    
    /**
     * 登录
     *
     * @param username 用户名
     * @return 登录结果
     */
    void logout(String username);

    /**
     * 已登录用户列表
     *
     * @return list
     */
    List<LoginInfoVO> list();
}
