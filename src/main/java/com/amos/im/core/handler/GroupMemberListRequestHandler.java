package com.amos.im.core.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.core.attribute.ImAttribute;
import com.amos.im.core.attribute.AttributeGroupUtil;
import com.amos.im.core.vo.LoginInfoVO;
import com.amos.im.core.command.request.GroupMemberListRequest;
import com.amos.im.core.command.response.GroupMemberListResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.List;
import java.util.Vector;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
@ChannelHandler.Sharable
public class GroupMemberListRequestHandler extends SimpleChannelInboundHandler<GroupMemberListRequest> {

    public static final GroupMemberListRequestHandler INSTANCE = new GroupMemberListRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMemberListRequest request) throws Exception {
        String groupId = request.getGroupId();
        ChannelGroup channels = AttributeGroupUtil.getChannelGroup(groupId);

        GroupMemberListResponse response = new GroupMemberListResponse();

        if (channels == null) {
            response.setSuccess(false);
            response.setGeneralCode(GeneralCode.GROUP_NOT_EXIST);
            ctx.channel().writeAndFlush(response);
            return;
        }

        response.setSuccess(true);
        response.setGroupInfoVO(AttributeGroupUtil.getGroupInfoServer(groupId));
        // List 群聊成员信息
        List<LoginInfoVO> list = new Vector<>();
        channels.forEach(channel -> list.add(channel.attr(ImAttribute.LOGIN_INFO).get()));
        response.setLoginInfoList(list);

        ctx.channel().writeAndFlush(response);
    }

}
