package com.amos.im.common.util;

import com.amos.im.core.command.response.GroupMessageResponse;
import com.amos.im.core.vo.GroupInfoVO;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/22
 */
public class PrintUtil {

    /**
     * 打印用户发的消息
     */
    public static void message(LocalDateTime createTime, String nickName, String message) {
        System.out.println(MessageFormat.format("\n\t{0}\n{1}:  {2}\n",
                createTime, nickName, message));
    }

    /**
     * 打印群信息
     */
    public static void join(String groupName, String groupId, String members) {
        System.out.println(
                MessageFormat.format("加入群聊成功, 群名{0}, 群号{1}, 成员{2}", groupName, groupId, members));
    }

    /**
     * 打印用户加入的所有群信息
     */
    public static void groups(List<GroupInfoVO> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("已加入的群聊: ");
        list.forEach(groupInfoVO -> sb.append(String.format("[%s](%s)\t", groupInfoVO.getGroupId(), groupInfoVO.getGroupName())));
        System.out.println(sb.toString());
    }

    /**
     * 打印用户发的消息
     */
    public static void groupMessage(GroupMessageResponse response, GroupInfoVO groupInfo) {
        System.out.println(String.format("\n[群消息] %s\t%tT\n%s:  %s\n",
                groupInfo.getGroupName(), response.getCreateTime(), response.getUsername(), response.getMessage()));
    }

//    public static void main(String[] args) {
//        System.out.println(String.format("\n%s\t%tT\n%s:  %s\n",
//                "群聊A", new Date(), "nickName", "message"));
//    }

}
