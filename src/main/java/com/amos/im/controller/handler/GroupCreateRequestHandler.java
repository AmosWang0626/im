package com.amos.im.controller.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.common.attribute.AttributeGroupUtil;
import com.amos.im.common.attribute.AttributeLoginUtil;
import com.amos.im.common.util.IdUtil;
import com.amos.im.controller.dto.GroupInfoVO;
import com.amos.im.controller.request.GroupCreateRequest;
import com.amos.im.controller.request.GroupCreateResponse;
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
public class GroupCreateRequestHandler extends SimpleChannelInboundHandler<GroupCreateRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequest groupCreateRequest) throws Exception {
        List<String> nickNameList = new ArrayList<>();

        // 群聊发起人
        String sponsor = groupCreateRequest.getSponsor();
        String groupName = groupCreateRequest.getGroupName();
        Date createTime = groupCreateRequest.getCreateTime();
        System.out.println(MessageFormat.format(
                "[{0}] {1} 发起建群请求 >>> 群名: {2}", createTime, sponsor, groupName));

        List<String> tokenList = groupCreateRequest.getTokenList();
        if (tokenList == null || tokenList.size() == 0) {
            System.out.println("群内不能没有成员!!!");
            GroupCreateResponse groupCreateResponse = new GroupCreateResponse();
            groupCreateResponse.setSuccess(false).setCreateTime(new Date())
                    .setGeneralCode(GeneralCode.CREATE_GROUP_FAIL);

            ctx.channel().writeAndFlush(groupCreateResponse);
            return;
        }

        String groupId = IdUtil.getInstance().getGroupId();

        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());
        tokenList.forEach(s -> {
            Channel channel = AttributeLoginUtil.getChannel(s);
            if (channel == null) {
                System.out.println(s + "用户未登录!!!");
                return;
            }
            nickNameList.add(AttributeLoginUtil.getLoginInfo(channel).getNickname());
            channels.add(channel);
        });

        GroupInfoVO groupInfoVO = new GroupInfoVO();
        groupInfoVO.setGroupId(groupId);
        groupInfoVO.setGroupName(groupName);
        groupInfoVO.setSponsorName(sponsor);
        groupInfoVO.setCreateTime(createTime);
        AttributeGroupUtil.createGroupServer(channels, groupInfoVO);

        GroupCreateResponse groupCreateResponse = new GroupCreateResponse();
        groupCreateResponse.setSuccess(true).setCreateTime(new Date())
                .setGroupId(groupId).setGroupName(groupName).setNicknameList(nickNameList)
                .setSponsorName(AttributeLoginUtil.getLoginInfo(ctx.channel()).getNickname());

        channels.writeAndFlush(groupCreateResponse);
    }

}
