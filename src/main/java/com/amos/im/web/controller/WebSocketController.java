package com.amos.im.web.controller;

import com.amos.im.core.business.LoginBusiness;
import com.amos.im.core.business.ServerBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private ServerBusiness serverBusiness;
    @Resource
    private LoginBusiness loginBusiness;


    @MessageMapping("/serverLogs")
    @SendTo("/server/logs")
    @ApiOperation("服务器日志")
    public List<String> serverLogs() {

        return serverBusiness.logs();
    }

    @MessageMapping("/clientLogs")
    @SendTo("/client/logs")
    @ApiOperation("客户端日志")
    public List<String> clientLogs() {

        return loginBusiness.logs();
    }

}
