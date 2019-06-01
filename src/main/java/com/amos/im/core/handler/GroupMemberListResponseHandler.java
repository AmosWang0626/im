package com.amos.im.core.handler;

import com.amos.im.core.vo.GroupInfoVO;
import com.amos.im.core.response.GroupMemberListResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Vector;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class GroupMemberListResponseHandler extends SimpleChannelInboundHandler<GroupMemberListResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMemberListResponse response) throws Exception {
        if (!response.getSuccess()) {
            System.out.println("获取成员列表失败, " + response.getGeneralCode().getMsg());
            return;
        }

        GroupInfoVO groupInfoVO = response.getGroupInfoVO();
        String groupInfo = String.format("群聊[%s](%s)成员列表: ", groupInfoVO.getGroupId(), groupInfoVO.getGroupName());

        List<String> list = new Vector<>();
        response.getLoginInfoVOS().forEach(loginInfoVO -> list.add(String.format("[%s](%s)", loginInfoVO.getToken(), loginInfoVO.getNickname())));
        System.out.println(groupInfo + list);
    }

}
