package com.amos.im.core.business.impl;

import com.amos.im.common.GeneralCode;
import com.amos.im.core.business.LoginBusiness;
import com.amos.im.core.command.request.LoginRequest;
import com.amos.im.core.command.response.LoginResponse;
import com.amos.im.core.constant.ImConstant;
import com.amos.im.core.pojo.vo.LoginInfoVO;
import com.amos.im.core.session.ServerSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 客户端核心业务
 *
 * @author amos
 * @date 2019/6/1
 */
@Service("loginBusiness")
public class LoginBusinessImpl implements LoginBusiness {


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSuccess(false);

        if (StringUtils.isBlank(loginRequest.getPassword())) {
            loginResponse.setGeneralCode(GeneralCode.LOGIN_FAIL_PASSWORD_EMPTY);
            return loginResponse;
        }

        if (!ImConstant.DEFAULT_PASSWORD.equals(loginRequest.getPassword())) {
            loginResponse.setGeneralCode(GeneralCode.LOGIN_FAIL_PASSWORD_ERROR);
            return loginResponse;
        }

        String username = loginRequest.getUsername();
        LoginInfoVO loginInfo = ServerSession.onlyUsername(username);
        if (loginInfo != null) {
            loginResponse.setGeneralCode(GeneralCode.LOGIN_FAIL_USERNAME_EXIST);
            return loginResponse;
        }

        loginResponse.setSuccess(true);
        loginResponse.setGeneralCode(GeneralCode.SUCCESS);
        return loginResponse;
    }


    @Override
    public List<LoginInfoVO> list() {
        return ServerSession.onlineList();
    }

}
