package com.amos.im.web.controller;

import com.amos.im.core.business.ServerBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 服务端 Controller
 *
 * @author amos
 * @date 2019/6/1
 */
@Api(tags = {"服务端接口"})
@RestController
@RequestMapping("server")
public class ServerController {

    @Resource
    private ServerBusiness serverBusiness;

    @GetMapping("/")
    @ApiOperation("Hi")
    public String base() {
        return "Hi, Server!";
    }

    /**
     * 启动服务端
     *
     * @return Message
     */
    @GetMapping("start")
    @ApiOperation("启动服务端")
    public String start() {
        serverBusiness.start();

        return "Server 启动成功!";
    }

    /**
     * 服务端日志
     *
     * @return Message
     */
    @GetMapping("logs")
    @ApiOperation("查看服务端日志")
    public List<String> logs() {

        return serverBusiness.logs();
    }

}
