package com.amos.im.core.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.response.LoginResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Daoyuan
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse loginResponse) {
        if (GeneralCode.SUCCESS.equals(loginResponse.getGeneralCode())) {
            // 客户端保存登录成功凭证
            AttributeLoginUtil.bindToken(ctx.channel(), loginResponse.getToken(), loginResponse.getNickname());
            System.out.println(">>>>>>>>> [客户端DEBUG] >>> ctx.channel(): " + ctx.channel() + ", toToken: " + loginResponse.getToken());

            System.out.println("[客户端] >>> " + loginResponse.getNickname() + " 登录成功, 可以聊天了!!!");
        } else {
            System.out.println("[客户端] >>> 登录失败!!! " + loginResponse.getGeneralCode().getMsg());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 客户端移除登录成功凭证
        AttributeLoginUtil.unBindToken(ctx.channel());
        super.channelInactive(ctx);
    }

}
