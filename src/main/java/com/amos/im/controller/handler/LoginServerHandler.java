package com.amos.im.controller.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.common.constant.ImConstant;
import com.amos.im.controller.request.LoginRequest;
import com.amos.im.controller.request.LoginResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Daoyuan
 */
public class LoginServerHandler extends SimpleChannelInboundHandler<LoginRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest msg) throws Exception {
        GeneralCode generalCode = GeneralCode.SUCCESS;
        LoginResponse response = new LoginResponse();
        if (validSuccess(msg)) {
            System.out.println("[服务端] >>> 客户端登录成功!!!");
            response.setNickname(desensitization(msg.getPhoneNo())).setToken(String.valueOf(System.currentTimeMillis()));
        } else {
            System.out.println("[服务端] >>> 客户端登录失败!!!");
            generalCode = GeneralCode.LOGIN_FAIL;
        }

        // 设置登录结果
        response.setGeneralCode(generalCode);

        ctx.channel().writeAndFlush(response);
    }

    /**
     * 校验登录信息
     *
     * @param loginRequest LoginRequest
     * @return true: 信息正确
     */
    private static boolean validSuccess(LoginRequest loginRequest) {
        return "18937128888".equals(loginRequest.getPhoneNo())
                && "123456".equals(loginRequest.getPassword());
    }

    /**
     * 手机号脱敏
     *
     * @param phoneNo 手机号
     * @return 脱敏手机号
     */
    private static String desensitization(String phoneNo) {
        if (phoneNo.length() != ImConstant.PHONE_NO_LENGTH) {
            return phoneNo.substring(0, 4);
        }

        return phoneNo.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
