package com.amos.im.web;

import com.amos.im.core.command.request.MessageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * DESCRIPTION: 这个类很简单，可以将Netty服务端启动的日志推送到前端
 *
 * @author amos
 * @date 2019/6/30
 */
@Controller
@Api(tags = {"Z1 WebSocket"})
public class WebSocketController {

    private final SimpMessagingTemplate messaging;

    public WebSocketController(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    @MessageMapping("/serverLogs")
    @SendTo("/server/logs")
    @ApiOperation("服务器日志")
    public String serverLogs(String logs) {
        return logs;
    }

    /**
     * 最简单的 WebSocket 单聊
     */
    @MessageMapping("/chat/alone")
    @ApiOperation("极简 WebSocket 单聊")
    public void alone(MessageRequest messageRequest) {
        String receiver = messageRequest.getReceiver();

        messaging.convertAndSend(
                "/client/push/alone/" + receiver, messageRequest);
    }

}
