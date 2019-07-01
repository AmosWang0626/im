package com.amos.im.core.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.common.util.LogUtils;
import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.command.response.LoginResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Daoyuan
 */
@Component
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponse> {

    @Resource
    private LogUtils logUtils;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse loginResponse) {
        if (GeneralCode.SUCCESS.equals(loginResponse.getGeneralCode())) {
            // 客户端保存登录成功凭证
            AttributeLoginUtil.bindToken(ctx.channel(), loginResponse.getToken(), loginResponse.getUsername());
            System.out.println(">>>>>>>>> [客户端DEBUG] >>> ctx.channel(): " + ctx.channel() + ", toToken: " + loginResponse.getToken());

            String tempLog = "[客户端] >>> " + loginResponse.getUsername() + " 登录成功, 可以聊天了!";
            logUtils.clientInfo(tempLog, this.getClass());
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
