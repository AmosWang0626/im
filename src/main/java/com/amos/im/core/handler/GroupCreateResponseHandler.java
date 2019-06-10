package com.amos.im.core.handler;

import com.amos.im.core.attribute.AttributeGroupUtil;
import com.amos.im.common.util.PrintUtil;
import com.amos.im.core.command.response.GroupCreateResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class GroupCreateResponseHandler extends SimpleChannelInboundHandler<GroupCreateResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateResponse groupCreateResponse) throws Exception {
        if (groupCreateResponse.getSuccess()) {
            String groupId = groupCreateResponse.getGroupId();
            String groupName = groupCreateResponse.getGroupName();

            // 只要加入群聊，则每个成员都会添加群聊信息
            AttributeGroupUtil.addGroupClient(ctx.channel(), groupId, groupName);

            PrintUtil.join(groupName, groupId, groupCreateResponse.getNicknameList().toString());
        }

    }

}
