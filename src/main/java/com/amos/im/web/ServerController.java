package com.amos.im.web;

import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.config.ImConfig;
import com.amos.im.core.constant.RedisKeys;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESCRIPTION: 服务端 Controller
 *
 * @author amos
 * @date 2019/6/1
 */
@Api(tags = {"A2 服务端"})
@RestController
@RequestMapping("server")
public class ServerController {

    @Resource
    private ImConfig imConfig;


    /**
     * 服务端地址
     *
     * @return Message
     */
    @GetMapping("ws")
    @ApiOperation("获取服务端WS地址")
    public String ws() {
        if (StringUtils.isNotBlank(imConfig.getWsDomain())) {
            return imConfig.getWsDomain();
        }

        return "ws://" + imConfig.getHost() + ":" + RedisUtil.get(RedisKeys.SERVER_RUN_PORT) + "/ws";
    }

    /**
     * 服务端日志
     *
     * @return Message
     */
    @GetMapping("logs")
    @ApiOperation("查看服务端日志")
    public List<String> logs() {

        return RedisUtil.lrange(RedisKeys.SERVER_RUN_LOG, 0, -1);
    }

}
