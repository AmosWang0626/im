package com.amos.im.core.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.constant.ImConstant;
import com.amos.im.common.util.IdUtil;
import com.amos.im.core.request.LoginRequest;
import com.amos.im.core.response.LoginResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * DESCRIPTION: 处理客户端发出的登录请求
 *
 * @author Daoyuan
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequest> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequest msg) throws Exception {
        GeneralCode generalCode = GeneralCode.SUCCESS;
        LoginResponse response = new LoginResponse();
        if (validSuccess(msg)) {
            String token = IdUtil.getInstance().getToken();
            String nickname = desensitization(msg.getPhoneNo());
            response.setNickname(nickname).setToken(token);

            // 保存客户端登录状态
            AttributeLoginUtil.bindToken(ctx.channel(), token, nickname);
            System.out.println(">>>>>>>>> [服务端DEBUG] >>> ctx.channel(): " + ctx.channel() + ", toToken: " + token);

            System.out.println("[服务端] >>> 客户端 [" + token + "](" + nickname + ")登录成功!!!");
        } else {
            System.out.println("[服务端] >>> 客户端登录失败!!!");
            generalCode = GeneralCode.LOGIN_FAIL;
        }

        // 设置登录结果
        response.setGeneralCode(generalCode);

        ctx.channel().writeAndFlush(response);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        AttributeLoginUtil.unBindToken(ctx.channel());
        super.channelInactive(ctx);
    }

    /**
     * 校验登录信息
     *
     * @param loginRequest LoginRequest
     * @return true: 信息正确
     */
    private static boolean validSuccess(LoginRequest loginRequest) {
        return "123456".equals(loginRequest.getPassword());
    }

    /**
     * 手机号脱敏
     *
     * @param phoneNo 手机号
     * @return 脱敏手机号
     */
    private static String desensitization(String phoneNo) {
        if (phoneNo.length() == ImConstant.PHONE_NO_LENGTH) {
            return phoneNo.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }

        if (phoneNo.length() > ImConstant.PHONE_NO_LENGTH) {
            return phoneNo.substring(0, ImConstant.PHONE_NO_LENGTH);
        }

        return phoneNo;
    }

}
