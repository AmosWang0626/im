package com.amos.im.controller.handler;

import com.amos.im.common.attribute.AttributeUtil;
import com.amos.im.common.util.IdUtil;
import com.amos.im.controller.request.CreateGroupRequest;
import com.amos.im.controller.request.CreateGroupResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
public class CreateGroupServerHandler extends SimpleChannelInboundHandler<CreateGroupRequest> {

    private List<String> nickNameList = new ArrayList<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequest createGroupRequest) throws Exception {
        String groupName = createGroupRequest.getGroupName();
        Date createTime = createGroupRequest.getCreateTime();
        System.out.println(MessageFormat.format("[{0}] {1} 发起建群请求 >>> 群名: {2}",
                createTime, createGroupRequest.getSponsor(), groupName));
        List<String> tokenList = createGroupRequest.getTokenList();
        if (tokenList == null || tokenList.size() == 0) {
            System.out.println("群内不能没有成员!!!");
            return;
        }

        String groupId = IdUtil.getInstance().getGroupId();

        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());
        tokenList.forEach(s -> {
            Channel channel = AttributeUtil.getChannel(s);
            if (channel == null) {
                System.out.println(s + "用户未登录!!!");
                return;
            }
            nickNameList.add(AttributeUtil.getToken(channel).getNickname());
            channels.add(channel);
        });

        CreateGroupResponse createGroupResponse = new CreateGroupResponse();
        createGroupResponse
                .setGroupId(groupId).setSuccess(true).setCreateTime(new Date())
                .setGroupName(groupName).setNicknameList(nickNameList)
                .setSponsorName(AttributeUtil.getToken(ctx.channel()).getNickname());

        channels.writeAndFlush(createGroupResponse);
    }

}
