package com.amos.im.web.console;

import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.request.GroupCreateRequest;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/26
 */
public class ConsoleCmdGroupCreate extends BaseConsole {

    @Override
    void exec(Channel channel) {
        // 群聊名字
        String groupName;
        // token集合
        List<String> tokenList;
        do {
            System.out.print("请输入群聊名字, 成员ID[用','分割]: ");
            groupName = sc.next();
            // 默认向群聊里添加自己
            String tokenStr = sc.nextLine() + "," + AttributeLoginUtil.getLoginInfo(channel).getToken();
            tokenList = Arrays.stream(tokenStr.split(",")).map(String::trim).distinct().collect(Collectors.toList());
        } while (tokenList.size() == 0);

        GroupCreateRequest groupCreateRequest = new GroupCreateRequest();
        groupCreateRequest.setSponsor(AttributeLoginUtil.getLoginInfo(channel).getToken())
                .setGroupName(groupName).setTokenList(tokenList).setCreateTime(new Date());

        channel.writeAndFlush(groupCreateRequest);
    }

}
