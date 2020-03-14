package com.amos.im.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.amos.im.common.GeneralCode;
import com.amos.im.common.util.IdUtil;
import com.amos.im.common.util.LogUtils;
import com.amos.im.core.command.request.LoginRequest;
import com.amos.im.core.command.response.LoginResponse;
import com.amos.im.core.constant.RedisKeys;
import com.amos.im.core.session.ServerSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * DESCRIPTION: 处理客户端发出的登录请求
 *
 * @author Daoyuan
 */
@ChannelHandler.Sharable
@Component
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequest> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest msg) throws Exception {
        LoginResponse response = new LoginResponse();
        response.setSuccess(true).setGeneralCode(GeneralCode.SUCCESS);

        String username = msg.getUsername();
        // 保存客户端登录状态
        if (StringUtils.isBlank(msg.getSender())) {
            String token = IdUtil.getInstance().getToken();
            response.setUsername(username).setToken(token);
            ServerSession.bindToken(ctx.channel(), token, username);
            String tempLog = "用户[" + token + "](" + username + ")登录成功! " + ctx.channel().id().asShortText();
            LogUtils.info(RedisKeys.SERVER_RUN_LOG, tempLog, this.getClass());
        } else {
            // 兼容已登录用户再次请求登录接口
            response.setUsername(username).setToken(msg.getSender());
        }

        ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(response)));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ServerSession.unBindToken(ctx.channel());
        super.channelInactive(ctx);
    }

}
