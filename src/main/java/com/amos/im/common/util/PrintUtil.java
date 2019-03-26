package com.amos.im.common.util;

import java.text.MessageFormat;
import java.util.Date;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/22
 */
public class PrintUtil {

    public static void message(Date createTime, String nickName, String message) {
        System.out.println(MessageFormat.format("\n\t{0}\n{1}:  {2}\n",
                createTime, nickName, message));
    }

    public static void join(String groupName, String groupId, String members) {
        System.out.println(
                MessageFormat.format("加入群聊成功, 群名{0}, 群号{1}, 成员{2}", groupName, groupId, members));
    }

}
