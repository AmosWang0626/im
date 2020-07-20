package com.amos.im.web;

import com.amos.im.core.business.LoginBusiness;
import com.amos.im.core.command.request.LoginRequest;
import com.amos.im.core.command.response.LoginResponse;
import com.amos.im.core.pojo.vo.LoginInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 用户
 *
 * @author amos
 * @date 2019/6/1
 */
@Api(tags = {"A1 登录相关"})
@RestController
@RequestMapping("user")
public class LoginController {

    @Resource
    private LoginBusiness loginBusiness;


    @PostMapping("login")
    @ApiOperation("用户登录")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        return loginBusiness.login(loginRequest);
    }

    @GetMapping("list")
    @ApiOperation("已登录用户列表")
    public List<LoginInfoVO> list() {

        return loginBusiness.list();
    }

}
