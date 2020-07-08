package com.amos.im.core.test;

import com.amos.im.common.util.RedisUtil;
import com.amos.im.core.config.ImConfig;
import com.amos.im.core.constant.RedisKeys;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模块名称: im
 * 模块描述: TODO
 *
 * @author amos.wang
 * @date 2020/7/8 19:10
 */
@Component
public class ServerHandler {

    @Resource
    private ImConfig imConfig;

    /**
     * 服务端地址
     */
    public Mono<ServerResponse> ws() {
        String wsUrl = imConfig.getHost() + ":" + RedisUtil.get(RedisKeys.SERVER_RUN_PORT);

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(wsUrl));
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
