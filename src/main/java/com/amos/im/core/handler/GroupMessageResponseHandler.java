package com.amos.im.core.handler;

import com.amos.im.core.attribute.AttributeGroupUtil;
import com.amos.im.common.util.PrintUtil;
import com.amos.im.core.pojo.vo.GroupInfoVO;
import com.amos.im.core.command.response.GroupMessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Daoyuan
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponse response) {
        if (!response.getSuccess()) {
            System.out.println("发送消息失败, " + response.getGeneralCode().getMsg());
            return;
        }

        GroupInfoVO groupInfo = AttributeGroupUtil.getGroupInfoClient(ctx.channel(), response.getFromGroup());
        PrintUtil.groupMessage(response, groupInfo);
    }

}
