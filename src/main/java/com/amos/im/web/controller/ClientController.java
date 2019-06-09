package com.amos.im.web.controller;

import com.amos.im.core.business.ClientBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @date 2019/6/2
 */
@Api(tags = {"客户端"})
@RestController
@RequestMapping("client")
public class ClientController {

    @Resource
    private ClientBusiness clientBusiness;

    @GetMapping("/")
    @ApiOperation("Hi")
    public String base() {
        return "Hi, Client!";
    }

    @GetMapping("logs")
    @ApiOperation("客户端日志")
    public List<String> logs() {

        return clientBusiness.logs();
    }

}
