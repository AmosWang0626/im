package com.amos.im.web.controller;

import com.amos.im.core.business.ClientBusiness;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * PROJECT: Sales
 * DESCRIPTION: 客户端 Controller
 *
 * @author amos
 * @date 2019/6/1
 */
@RestController
@RequestMapping("client")
public class ClientController {

    @Resource
    private ClientBusiness clientBusiness;

    @RequestMapping("/")
    public String base() {
        return "Client";
    }

    @GetMapping("start")
    public String start() {
        clientBusiness.start();

        return "客户端启动成功!";
    }

    @GetMapping("logs")
    public List<String> logs() {

        return clientBusiness.logs();
    }

}
