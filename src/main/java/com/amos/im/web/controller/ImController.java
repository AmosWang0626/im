package com.amos.im.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PROJECT: Sales
 * DESCRIPTION: 本项目通用 Controller
 *
 * @author amos
 * @date 2019/6/1
 */
@RestController
public class ImController {

    @Value("${im.flag}")
    private String imFlag;

    @RequestMapping("/")
    public String base() {
        return "Hello, Welcome to Im System ! Current environment " + imFlag;
    }

}
