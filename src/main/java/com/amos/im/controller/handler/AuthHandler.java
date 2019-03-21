package com.amos.im.controller.handler;

import com.amos.im.common.attribute.AttributeUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/21
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (AttributeUtil.hasLogin(ctx.channel())) {
            // 移除登录校验Handler
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        } else {
            ctx.channel().close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        if (AttributeUtil.hasLogin(ctx.channel())) {
//            System.out.println("身份验证完成 <<<安全>>> AuthHandler 已移除!");
//        } else {
//            System.out.println("身份验证完成 >>>!!!未登录!!!<<< 强制关闭连接!");
//        }
    }
}
