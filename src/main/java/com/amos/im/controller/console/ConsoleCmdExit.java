package com.amos.im.controller.console;

import io.netty.channel.Channel;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/26
 */
public class ConsoleCmdExit extends BaseConsole {

    @Override
    public void exec(Channel channel) {
        Thread.currentThread().interrupt();
        System.out.println("关闭客户端!");
        System.exit(0);
    }

}
