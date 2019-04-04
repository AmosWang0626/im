package com.amos.im.controller.console;

import com.amos.im.common.attribute.AttributeGroupUtil;
import com.amos.im.common.util.PrintUtil;
import com.amos.im.controller.dto.GroupInfoVO;
import com.amos.im.controller.request.MessageRequest;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.List;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/26
 */
public class ConsoleCmdGroupMessage extends BaseConsole {

    @Override
    void exec(Channel channel) {
        // message(channel);
        List<GroupInfoVO> groupInfoList = AttributeGroupUtil.getGroupInfo(channel);
        PrintUtil.groups(groupInfoList);
    }

    private void message(Channel channel) {
        String token = sc.next();
        String message = sc.nextLine();

        // 对首个输入校验是否是退出标识
        backConsoleManager(channel, token);

        Date sendTime = new Date();
        MessageRequest request = new MessageRequest();
        request.setToToken(token).setMessage(message).setCreateTime(new Date());
        channel.writeAndFlush(request);

        PrintUtil.message(sendTime, "我", message);
    }

}
