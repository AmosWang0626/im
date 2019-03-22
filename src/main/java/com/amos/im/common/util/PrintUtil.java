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

    public static void println(Date createTime, String nickName, String message) {
        System.out.println(MessageFormat.format("\n\t{0}\n{1}:  {2}\n",
                createTime, nickName, message));
    }

}
