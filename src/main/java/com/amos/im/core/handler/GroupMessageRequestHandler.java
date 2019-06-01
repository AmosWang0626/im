package com.amos.im.core.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.core.attribute.AttributeGroupUtil;
import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.vo.LoginInfoVO;
import com.amos.im.core.request.GroupMessageRequest;
import com.amos.im.core.response.GroupMessageResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.text.MessageFormat;
import java.util.Date;

/**
 * DESCRIPTION: 服务端 >>> 转发消息 到 指定客户端
 *
 * @author Daoyuan
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequest> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequest request) {
        LoginInfoVO loginInfo = AttributeLoginUtil.getLoginInfo(ctx.channel());
        System.out.println(MessageFormat.format("[{0}] >>> {1}, {2}",
                loginInfo.getNickname(), request.getCreateTime(), request.getMessage()));

        GroupMessageResponse response = new GroupMessageResponse();

        // 根据消息中指定的token，发送给对应用户
        ChannelGroup channels = AttributeGroupUtil.getChannelGroup(request.getToGroup());
        if (channels == null) {
            response.setSuccess(false);
            response.setGeneralCode(GeneralCode.GROUP_NOT_EXIST);
            ctx.channel().writeAndFlush(response);
            return;
        }

        response.setFromGroup(request.getToGroup()).setNickName(loginInfo.getNickname())
                .setMessage(request.getMessage()).setSuccess(true).setCreateTime(new Date());
        channels.writeAndFlush(response);
    }

}