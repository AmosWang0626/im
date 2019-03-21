package com.amos.im.controller.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.common.attribute.AttributeUtil;
import com.amos.im.request.LoginRequest;
import com.amos.im.request.LoginResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Daoyuan
 */
public class LoginClientHandler extends SimpleChannelInboundHandler<LoginResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse msg) throws Exception {
        if (GeneralCode.SUCCESS.equals(msg.getGeneralCode())) {
            System.out.println("[客户端] >>> " + msg.getNickname() + " 登录成功, 可以聊天了!!!");
            // 设置登录状态为 true || token
            AttributeUtil.maskLogin(ctx.channel());
            AttributeUtil.maskToken(ctx.channel(), String.valueOf(System.currentTimeMillis()));
        } else {
            System.out.println("[客户端] >>> 登录失败!!! " + msg.getGeneralCode().getMsg());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[客户端] >>> 开始登录...");
        LoginRequest loginRequest = new LoginRequest().setPhoneNo("18937128888").setPassword("123456");
        ctx.channel().writeAndFlush(loginRequest);
    }
}
