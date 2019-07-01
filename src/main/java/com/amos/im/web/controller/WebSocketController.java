package com.amos.im.web.controller;

import com.amos.im.core.business.LoginBusiness;
import com.amos.im.core.business.ServerBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Collections;
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
    public List<String> serverLogs(String logs) {
        if (StringUtils.isBlank(logs)) {
            return serverBusiness.logs();
        }

        return Collections.singletonList(logs);
    }

    @MessageMapping("/clientLogs")
    @SendTo("/client/logs")
    @ApiOperation("客户端日志")
    public List<String> clientLogs(String logs) {
        if (StringUtils.isBlank(logs)) {
            return loginBusiness.logs();
        }

        return Collections.singletonList(logs);
    }

}
