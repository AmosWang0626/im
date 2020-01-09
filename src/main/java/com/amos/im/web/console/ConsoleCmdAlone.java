package com.amos.im.web.console;

import com.amos.im.common.util.PrintUtil;
import com.amos.im.core.command.request.MessageRequest;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/26
 */
public class ConsoleCmdAlone extends BaseConsole {

    @Override
    void exec(Channel channel) {
        System.out.println("请输入接收人ID和消息: ");
        String token = sc.next();
        String message = sc.nextLine();

        // 校验发送的消息
        if (StringUtils.isBlank(message)) {
            System.out.println("发送的消息不能为null");
            return;
        }

        // 对首个输入校验是否是退出标识
        backConsoleManager(channel, token);

        MessageRequest request = new MessageRequest();
        request.setReceiver(token).setMessage(message).setCreateTime(LocalDateTime.now());
        channel.writeAndFlush(request);

        PrintUtil.message(request.getCreateTime(), "我", message);
    }

}
