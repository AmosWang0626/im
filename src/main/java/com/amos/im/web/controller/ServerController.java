package com.amos.im.web.controller;

import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.config.ImConfig;
import com.amos.im.core.constant.RedisKeys;
import com.amos.im.core.service.ServerService;
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
@Api(tags = {"服务端"})
@RestController
@RequestMapping("server")
public class ServerController {

    @Resource
    private ImConfig imConfig;
    @Resource
    private ServerService serverService;

    /**
     * 服务端地址
     *
     * @return Message
     */
    @GetMapping("ws")
    @ApiOperation("查看服务端日志")
    public String ws() {
        return imConfig.getHost() + ":" + RedisUtil.get(RedisKeys.SERVER_RUN_PORT);
    }

    /**
     * 服务端日志
     *
     * @return Message
     */
    @GetMapping("logs")
    @ApiOperation("查看服务端日志")
    public List<String> logs() {
        return serverService.logs();
    }

}
