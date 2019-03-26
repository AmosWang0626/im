package com.amos.im.controller.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public interface Console {

    Scanner SC = new Scanner(System.in);

    void sign(Channel channel);

    void exec(Channel channel);

}
