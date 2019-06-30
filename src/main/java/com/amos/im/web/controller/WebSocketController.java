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

    @MessageMapping("/message")
    @SendTo("/ws/message")
    @ApiOperation("message")
    public String message(String message) throws Exception {
        Thread.sleep(1000);
        return "Hello " + message + ", welcome websocket!";
    }

}
