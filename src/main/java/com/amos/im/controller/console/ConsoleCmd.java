package com.amos.im.controller.console;

import java.util.ArrayList;
import java.util.List;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public enum ConsoleCmd {

    /**
     * 登录
     */
    LOGIN("login"),
    /**
     * 单聊
     */
    ALONE("alone"),
    /**
     * 群聊
     */
    GROUP_CREATE("create"),
    GROUP_JOIN("join"),
    GROUP_LIST("list"),
    GROUP_QUIT("quit"),
    GROUP_MESSAGE("msg"),
    /**
     * 退出 or 返回控制台
     */
    EXIT("exit");

    ConsoleCmd(String cmd) {
        this.cmd = cmd;
    }

    private final String cmd;

    public String getCmd() {
        return cmd;
    }

    public static ConsoleCmd get(String cmd) {
        for (ConsoleCmd consoleCmdEnum : ConsoleCmd.values()) {
            if (consoleCmdEnum.getCmd().equalsIgnoreCase(cmd)) {
                return consoleCmdEnum;
            }
        }

        return null;
    }

    public static List<String> getAllCmd() {
        List<String> list = new ArrayList<>(ConsoleCmd.values().length);
        for (ConsoleCmd consoleCmd : ConsoleCmd.values()) {
            list.add(consoleCmd.getCmd());
        }

        return list;
    }

//    public static void main(String[] args) {
//        System.out.message(ConsoleCmd.get("exit"));
//        System.out.message(ConsoleCmd.getAllCmd());
//    }
}
