package com.amos.im.controller.handler;

import com.amos.im.common.attribute.AttributeGroupUtil;
import com.amos.im.controller.dto.GroupInfoVO;
import com.amos.im.controller.request.GroupJoinResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class GroupJoinResponseHandler extends SimpleChannelInboundHandler<GroupJoinResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinResponse response) throws Exception {
        if (!response.getSuccess()) {
            System.out.println(response.getGeneralCode().getMsg());
            return;
        }
        String nickName = response.getNickName();
        System.out.println(nickName + " 加入群聊成功!");

        // 客户端保存群聊信息(稍有冗余)
        GroupInfoVO groupInfoVO = response.getGroupInfoVO();
        AttributeGroupUtil.addGroupClient(ctx.channel(), groupInfoVO.getGroupId(), groupInfoVO.getGroupName());
    }

}
