package com.amos.im.web.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public abstract class BaseConsole {

    Scanner sc = new Scanner(System.in);

    /**
     * 客户端执行命令
     *
     * @param channel channel
     */
    abstract void exec(Channel channel);

    /**
     * 返回控制台
     *
     * @param channel Channel
     * @param input   用户输入
     */
    void backConsoleManager(Channel channel, String input) {
        if (ConsoleCmd.EXIT.getCmd().equals(input)) {
            ConsoleManager.newInstance().exec(channel);
        }
    }
}
