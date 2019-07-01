package com.amos.im.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * PROJECT: Sales
 * DESCRIPTION: WebSocket Controller
 *
 * @author amos
 * @date 2019/6/30
 */
@Controller
@Api(tags = {"WebSocket"})
public class WebSocketController {

    @MessageMapping("/serverLogs")
    @SendTo("/server/logs")
    @ApiOperation("服务器日志")
    public String serverLogs(String logs) {
        return logs;
    }

    @MessageMapping("/clientLogs")
    @SendTo("/client/logs")
    @ApiOperation("客户端日志")
    public String clientLogs(String logs) {
        return logs;
    }

}
