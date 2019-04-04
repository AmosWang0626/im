package com.amos.im.common.util;

import com.amos.im.controller.dto.GroupInfoVO;

import java.text.MessageFormat;
import java.util.Date;
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
    public static void message(Date createTime, String nickName, String message) {
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
        if (list == null || list.size() == 0) {
            System.out.println("您还未加入任何群!");
            return;
        }
        list.forEach(groupInfoVO -> System.out.println(String.format("已加入的群聊: [%s](%s)", groupInfoVO.getGroupId(), groupInfoVO.getGroupName())));
    }

}
