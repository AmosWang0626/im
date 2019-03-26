package com.amos.im.controller.console;

import com.amos.im.common.attribute.AttributeUtil;
import com.amos.im.common.util.PrintUtil;
import com.amos.im.controller.request.CreateGroupRequest;
import com.amos.im.controller.request.LoginRequest;
import com.amos.im.controller.request.MessageRequest;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class ConsoleImpl implements Console {

    private static CmdEnum NOW_CMD = null;


    @Override
    public void sign(Channel channel) {
        exec(channel, CmdEnum.LOGIN);
    }

    @Override
    public void exec(Channel channel) {
        if (NOW_CMD == null) {
            List<String> allCmd = CmdEnum.getAllCmd();
            allCmd.remove(CmdEnum.LOGIN.getCmd());
            allCmd.remove(CmdEnum.LOGOUT.getCmd());
            do {
                System.out.print("请输入您要执行的操作" + allCmd + ": ");
                NOW_CMD = CmdEnum.get(SC.next());
            } while (NOW_CMD == null);
        }
        exec(channel, NOW_CMD);
    }

    private void exec(Channel channel, CmdEnum cmdEnum) {
        switch (cmdEnum) {
            case LOGIN:
                login(channel);
                break;

            case LOGOUT:
                logout(channel);
                break;

            case ALONE:
                alone(channel);
                break;

            case GROUP:
                group(channel);
                break;

            case EXIT:
                exit(channel);
                break;

            default:
                break;
        }
        waitServer();
    }

    private void login(Channel channel) {
        System.out.print("请输入用户名登录: ");
        String phoneNo = SC.next();
        // String password = SC.next();
        String password = "123456";

        LoginRequest loginRequest = new LoginRequest().setPhoneNo(phoneNo).setPassword(password);
        channel.writeAndFlush(loginRequest);
    }

    private void logout(Channel channel) {
        // exit(channel);
    }

    private void alone(Channel channel) {
        String token = SC.next();
        String message = SC.nextLine();

        // 对首个输入校验是否是退出标识
        checkExit(channel, token);

        Date sendTime = new Date();
        MessageRequest request = new MessageRequest();
        request.setToToken(token).setMessage(message).setCreateTime(new Date());
        channel.writeAndFlush(request);

        PrintUtil.println(sendTime, "我", message);
    }

    private void group(Channel channel) {
        // 群聊名字
        String groupName;
        // 成员token
        String tokenStr;
        // split后的token数组
        String[] tokens;
        // trim处理后的token集合
        List<String> tokenList = new ArrayList<>();
        do {
            System.out.print("请输入群聊名字, 成员ID[用','分割]: ");
            groupName = SC.next();
            tokenStr = SC.next() + SC.nextLine();
            tokens = tokenStr.split(",");
            if (tokens.length > 0) {
                for (String token : tokens) {
                    String tokenTrim = token.trim();
                    if (tokenTrim.length() > 0) {
                        tokenList.add(tokenTrim);
                    }
                }
            }
        } while (tokenList.size() > 0);

        tokenList.add(AttributeUtil.getToken(channel).getToken());
        CreateGroupRequest createGroupRequest = new CreateGroupRequest();
        createGroupRequest.setSponsor(AttributeUtil.getToken(channel).getToken()).setGroupName(groupName).
                setTokenList(tokenList).setCreateTime(new Date());

        channel.writeAndFlush(createGroupRequest);
    }

    private void exit(Channel channel) {
        if (CmdEnum.EXIT.equals(NOW_CMD)) {
            Thread.currentThread().interrupt();
            System.out.println("关闭客户端!");
            System.exit(0);
            return;
        }
        NOW_CMD = null;
        exec(channel);
    }

    /**
     * 监听用户输入的首个字符串
     */
    private void checkExit(Channel channel, String exit) {
        if (CmdEnum.EXIT.equals(CmdEnum.get(exit))) {
            exit(channel);
        }
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
