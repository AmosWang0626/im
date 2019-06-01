package com.amos.im.core.handler;

import com.amos.im.core.attribute.AttributeGroupUtil;
import com.amos.im.core.response.GroupQuitResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class GroupQuitResponseHandler extends SimpleChannelInboundHandler<GroupQuitResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitResponse response) throws Exception {
        if (!response.getSuccess()) {
            System.out.println("退出群聊失败, " + response.getGeneralCode().getMsg());
            return;
        }
        String groupId = response.getGroupId();
        AttributeGroupUtil.quitGroupClient(ctx.channel(), groupId);
        System.out.println(String.format("退出群聊%s成功", groupId));
    }

}
