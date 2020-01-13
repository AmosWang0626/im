package com.amos.im.core.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.core.attribute.AttributeGroupUtil;
import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.attribute.ImAttribute;
import com.amos.im.core.command.request.GroupJoinRequest;
import com.amos.im.core.command.response.GroupJoinResponse;
import com.amos.im.core.pojo.vo.LoginInfoVO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.time.LocalDateTime;
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
public class GroupJoinRequestHandler extends SimpleChannelInboundHandler<GroupJoinRequest> {

    public static final GroupJoinRequestHandler INSTANCE = new GroupJoinRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequest request) throws Exception {
        Channel inxChannel = ctx.channel();

        String groupId = request.getGroupId();
        ChannelGroup channels = AttributeGroupUtil.getChannelGroup(groupId);

        GroupJoinResponse response = new GroupJoinResponse();

        if (channels == null) {
            response.setSuccess(false);
            response.setGeneralCode(GeneralCode.GROUP_NOT_EXIST);
            inxChannel.writeAndFlush(response);
            return;
        }

        if (channels.contains(inxChannel)) {
            response.setSuccess(false);
            response.setGeneralCode(GeneralCode.YOU_HAVE_JOINED_THE_GROUP);
            inxChannel.writeAndFlush(response);
            return;
        }

        LoginInfoVO loginInfo = AttributeLoginUtil.getLoginInfo(inxChannel);

        channels.add(inxChannel);
        AttributeGroupUtil.updateGroupServer(groupId, channels);

        response.setSuccess(true);
        response.setCreateTime(LocalDateTime.now());
        response.setUsername(loginInfo.getUsername());
        response.setGroupInfoVO(AttributeGroupUtil.getGroupInfoServer(groupId));

        channels.writeAndFlush(response);

        List<String> list = new Vector<>();

        channels.forEach(channel -> {
            LoginInfoVO loginInfoVO = channel.attr(ImAttribute.LOGIN_INFO).get();
            list.add(String.format("[%s](%s)", loginInfoVO.getToken(), loginInfoVO.getUsername()));
        });
        System.out.println("群聊成员: " + list);
    }

}
