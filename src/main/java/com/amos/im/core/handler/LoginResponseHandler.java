package com.amos.im.core.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.common.util.LogUtils;
import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.command.response.LoginResponse;
import com.amos.im.core.constant.RedisKeys;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * @author Daoyuan
 */
@Component
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse loginResponse) {
        if (GeneralCode.SUCCESS.equals(loginResponse.getGeneralCode())) {
            // 客户端保存登录成功凭证
            AttributeLoginUtil.bindToken(ctx.channel(), loginResponse.getToken(), loginResponse.getUsername());
            System.out.println(">>>>>>>>> [客户端DEBUG] >>> ctx.channel(): " + ctx.channel() + ", toToken: " + loginResponse.getToken());

            String tempLog = "[客户端] >>> " + loginResponse.getUsername() + " 登录成功, 可以聊天了!";
            LogUtils.info(RedisKeys.CLIENT_RUN_LOG, tempLog, this.getClass());
        } else {
            System.out.println("[客户端] >>> 登录失败!!! " + loginResponse.getGeneralCode().getMsg());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        AttributeLoginUtil.unBindToken(ctx.channel());
        super.channelInactive(ctx);
    }

}
