package com.amos.im.web.console;

import com.amos.im.core.attribute.AttributeGroupUtil;
import com.amos.im.common.util.PrintUtil;
import com.amos.im.core.vo.GroupInfoVO;
import com.amos.im.core.command.request.GroupMessageRequest;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

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
        // 打印已加入的群聊
        List<GroupInfoVO> groupInfoList = AttributeGroupUtil.getGroupInfoClient(channel);
        if (CollectionUtils.isEmpty(groupInfoList)) {
            System.out.println("您还未加入群聊, 故不能发送群消息!");
            return;
        }
        PrintUtil.groups(groupInfoList);

        // 发送消息逻辑
        message(channel);
    }

    private void message(Channel channel) {
        System.out.println("请输入群聊ID和消息: ");
        String groupId = sc.next();
        String message = sc.nextLine();

        // 校验发送的消息
        if (StringUtils.isBlank(message)) {
            System.out.println("发送的群消息不能为null");
            return;
        }

        // 对首个输入校验是否是退出标识
        backConsoleManager(channel, groupId);

        GroupMessageRequest request = new GroupMessageRequest();
        request.setToGroup(groupId).setMessage(message).setCreateTime(new Date());
        channel.writeAndFlush(request);
    }

}
