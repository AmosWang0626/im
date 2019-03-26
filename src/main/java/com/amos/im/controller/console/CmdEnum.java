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
public enum CmdEnum {

    LOGIN("login"),
    LOGOUT("logout"),
    ALONE("alone"),
    GROUP("group"),
    EXIT("exit");

    CmdEnum(String cmd) {
        this.cmd = cmd;
    }

    private final String cmd;

    public String getCmd() {
        return cmd;
    }

    public static CmdEnum get(String cmd) {
        for (CmdEnum cmdEnum : CmdEnum.values()) {
            if (cmdEnum.getCmd().equalsIgnoreCase(cmd)) {
                return cmdEnum;
            }
        }

        return null;
    }

    public static List<String> getAllCmd() {
        List<String> list = new ArrayList<>(CmdEnum.values().length);
        for (CmdEnum cmdEnum : CmdEnum.values()) {
            list.add(cmdEnum.getCmd());
        }

        return list;
    }

//    public static void main(String[] args) {
//        System.out.println(CmdEnum.get("exit"));
//        System.out.println(CmdEnum.getAllCmd());
//    }
}
