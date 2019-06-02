package com.amos.im.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PROJECT: Sales
 * DESCRIPTION: 本项目通用 Controller
 *
 * @author amos
 * @date 2019/6/1
 */
@Api(tags = {"IM通用接口"})
@RestController
public class ImController {

    @Value("${im.flag}")
    private String imFlag;

    @GetMapping("/")
    @ApiOperation("Hello")
    public String base() {
        return "Hello IM!";
    }

    @GetMapping("env")
    @ApiOperation("当前环境")
    public String env() {
        return imFlag;
    }

}
