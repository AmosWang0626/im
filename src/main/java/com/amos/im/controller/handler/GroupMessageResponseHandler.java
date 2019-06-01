package com.amos.im.controller.handler;

import com.amos.im.common.attribute.AttributeGroupUtil;
import com.amos.im.common.util.PrintUtil;
import com.amos.im.controller.dto.GroupInfoVO;
import com.amos.im.controller.request.GroupMessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Daoyuan
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponse> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, GroupMessageResponse response) {
        if (!response.getSuccess()) {
            System.out.println("发送消息失败, " + response.getGeneralCode().getMsg());
            return;
        }

        GroupInfoVO groupInfo = AttributeGroupUtil.getGroupInfoClient(ctx.channel(), response.getFromGroup());
        PrintUtil.groupMessage(response, groupInfo);
    }

}
