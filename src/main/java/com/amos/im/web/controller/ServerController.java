package com.amos.im.web.controller;

import com.amos.im.core.business.ServerBusiness;
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
@RestController
@RequestMapping("server")
public class ServerController {

    @Resource
    private ServerBusiness serverBusiness;

    @RequestMapping("/")
    public String base() {
        return "Server";
    }

    /**
     * 启动服务端
     *
     * @return Message
     */
    @GetMapping("start")
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
    public List<String> logs() {

        return serverBusiness.logs();
    }

}
