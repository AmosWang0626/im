package com.amos.im.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/")
    public String base() {
        return "Server";
    }

}
