package com.amos.im.web.controller;

import com.amos.im.core.business.AloneBusiness;
import com.amos.im.core.command.request.MessageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * PROJECT: Sales
 * DESCRIPTION: WebSocket 单聊
 *
 * @author amos
 * @date 2019/6/2
 */
@Controller
@Api(tags = {"WebSocket"})
public class WebSocketAloneController {

    @Resource
    private AloneBusiness aloneBusiness;

    @MessageMapping("/chat/alone")
    @ApiOperation("websocket 单聊")
    public void alone(MessageRequest messageRequest) {

        aloneBusiness.websocketAlone(messageRequest);
    }

}
