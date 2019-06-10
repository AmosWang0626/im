package com.amos.im.web.console;

import com.amos.im.core.command.request.LoginRequest;
import io.netty.channel.Channel;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/26
 */
public class ConsoleCmdLogin extends BaseConsole {

    @Override
    public void exec(Channel channel) {
        System.out.print("请输入用户名登录: ");
        String username = sc.next();
        /*String password = sc.next();*/
        String password = "123456";

        LoginRequest loginRequest = new LoginRequest().setUsername(username).setPassword(password);
        channel.writeAndFlush(loginRequest);
    }

}
