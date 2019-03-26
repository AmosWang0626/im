package com.amos.im.controller.handler;

import com.amos.im.controller.request.CreateGroupResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.text.MessageFormat;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class CreateGroupClientHandler extends SimpleChannelInboundHandler<CreateGroupResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponse createGroupResponse) throws Exception {
        System.out.println(MessageFormat.format("加入群聊成功, 群名{0}, 群号{1}, 成员{2}",
                createGroupResponse.getGroupName(), createGroupResponse.getGroupId(), createGroupResponse.getNicknameList()));
    }

}
