package com.amos.im.controller.console;

import com.amos.im.common.attribute.AttributeLoginUtil;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class ConsoleManager extends BaseConsole {

    private static Map<ConsoleCmd, BaseConsole> CMD_MAP = new ConcurrentHashMap<>();

    private static ConsoleManager consoleManager;

    public static ConsoleManager newInstance() {
        if (consoleManager == null) {
            consoleManager = new ConsoleManager();
        }
        return consoleManager;
    }

    private ConsoleManager() {
        CMD_MAP.put(ConsoleCmd.LOGIN, new ConsoleCmdLogin());
        CMD_MAP.put(ConsoleCmd.EXIT, new ConsoleCmdExit());
        // 单聊
        CMD_MAP.put(ConsoleCmd.ALONE, new ConsoleCmdAlone());
        // 群组
        CMD_MAP.put(ConsoleCmd.GROUP_CREATE, new ConsoleCmdGroupCreate());
        CMD_MAP.put(ConsoleCmd.GROUP_JOIN, new ConsoleCmdGroupJoin());
        CMD_MAP.put(ConsoleCmd.GROUP_LIST, new ConsoleCmdGroupList());
        CMD_MAP.put(ConsoleCmd.GROUP_QUIT, new ConsoleCmdGroupQuit());
        CMD_MAP.put(ConsoleCmd.GROUP_MESSAGE, new ConsoleCmdGroupMessage());
    }

    @Override
    public void exec(Channel channel) {
        if (AttributeLoginUtil.hasLogin(channel)) {
            List<String> allCmd = ConsoleCmd.getAllCmd();
            allCmd.remove(ConsoleCmd.LOGIN.getCmd());

            ConsoleCmd consoleCmd;
            do {
                System.out.print("请输入您要执行的操作" + allCmd + ": ");
                consoleCmd = ConsoleCmd.get(sc.next());
            } while (consoleCmd == null);

            CMD_MAP.get(consoleCmd).exec(channel);
        } else {
            // 未登录则登录
            CMD_MAP.get(ConsoleCmd.LOGIN).exec(channel);
        }

        // 等待服务器响应
        waitServer();
    }

    /**
     * 等待服务端响应延迟1秒
     */
    private static void waitServer() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
