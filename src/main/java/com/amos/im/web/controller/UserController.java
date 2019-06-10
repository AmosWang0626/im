package com.amos.im.web.controller;

import com.amos.im.core.business.ClientBusiness;
import com.amos.im.core.command.request.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * PROJECT: Sales
 * DESCRIPTION: 用户
 *
 * @author amos
 * @date 2019/6/1
 */
@Api(tags = {"客户端", "登录相关"})
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private ClientBusiness clientBusiness;


    @PostMapping("login")
    @ApiOperation("用户登录")
    public String login(@RequestBody LoginRequest loginRequest) {

        return clientBusiness.login(loginRequest);
    }

}
