package com.amos.im.web.controller;

import com.amos.im.core.business.LoginBusiness;
import com.amos.im.core.command.request.LoginRequest;
import com.amos.im.core.pojo.vo.LoginInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 用户
 *
 * @author amos
 * @date 2019/6/1
 */
@Api(tags = {"客户端"})
@RestController
@RequestMapping("user")
public class ClientController {

    @Resource
    private LoginBusiness loginBusiness;

    @GetMapping("logs")
    @ApiOperation("登录日志")
    public List<String> logs() {

        return loginBusiness.logs();
    }

    @PostMapping("login")
    @ApiOperation("用户登录")
    public String login(@RequestBody LoginRequest loginRequest) {

        return loginBusiness.login(loginRequest);
    }

    @GetMapping("list")
    @ApiOperation("已登录用户列表")
    public List<LoginInfoVO> list() {

        return loginBusiness.list();
    }

}
