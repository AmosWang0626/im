package com.amos.im.core.handler;

import com.amos.im.common.GeneralCode;
import com.amos.im.common.util.IdUtil;
import com.amos.im.core.attribute.AttributeGroupUtil;
import com.amos.im.core.attribute.AttributeLoginUtil;
import com.amos.im.core.command.request.GroupCreateRequest;
import com.amos.im.core.command.response.GroupCreateResponse;
import com.amos.im.core.vo.GroupInfoVO;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/23
 */
@ChannelHandler.Sharable
public class GroupCreateRequestHandler extends SimpleChannelInboundHandler<GroupCreateRequest> {

    public static final GroupCreateRequestHandler INSTANCE = new GroupCreateRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequest groupCreateRequest) throws Exception {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("create-group-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(2, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.submit(() -> createGroupCore(ctx, groupCreateRequest));

        singleThreadPool.shutdown();
    }

    private void createGroupCore(ChannelHandlerContext ctx, GroupCreateRequest groupCreateRequest) {
        long startTime = System.currentTimeMillis();
        List<String> nickNameList = new ArrayList<>();

        // 群聊基本信息
        String sponsor = groupCreateRequest.getSponsor();
        String groupName = groupCreateRequest.getGroupName();
        LocalDateTime createTime = groupCreateRequest.getCreateTime();
        System.out.println(MessageFormat.format(
                "[{0}] {1} 发起建群请求 >>> 群名: {2}", createTime, sponsor, groupName));

        // 校验群成员不能为null
        List<String> tokenList = groupCreateRequest.getTokenList();
        if (tokenList == null || tokenList.size() == 0) {
            System.out.println("群内不能没有成员!!!");
            GroupCreateResponse groupCreateResponse = new GroupCreateResponse();
            groupCreateResponse.setSuccess(false).setCreateTime(LocalDateTime.now())
                    .setGeneralCode(GeneralCode.CREATE_GROUP_FAIL);

            ctx.channel().writeAndFlush(groupCreateResponse);
            return;
        }

        // 生成群ID
        String groupId = IdUtil.getInstance().getGroupId();

        // 如果用户没登录暂时不拉
        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());
        tokenList.forEach(s -> {
            Channel channel = AttributeLoginUtil.getChannel(s);
            if (channel == null) {
                System.out.println(s + "用户未登录!!!");
                return;
            }
            nickNameList.add(AttributeLoginUtil.getLoginInfo(channel).getUsername());
            channels.add(channel);
        });

        // 服务端保存群聊信息
        GroupInfoVO groupInfoVO = new GroupInfoVO();
        groupInfoVO.setGroupId(groupId).setGroupName(groupName).setSponsorName(sponsor).setCreateTime(createTime);
        AttributeGroupUtil.createGroupServer(channels, groupInfoVO);

        // 通知用户加入群聊
        GroupCreateResponse groupCreateResponse = new GroupCreateResponse();
        groupCreateResponse.setGroupId(groupId).setGroupName(groupName)
                .setUsernameList(nickNameList).setSponsorName(AttributeLoginUtil.getLoginInfo(ctx.channel()).getUsername())
                .setSuccess(true).setCreateTime(LocalDateTime.now());

        channels.writeAndFlush(groupCreateResponse).addListener(future -> {
            // 统计执行耗时
            if (future.isDone()) {
                System.out.println(String.format("创建群聊耗时: %d", System.currentTimeMillis() - startTime));
            }
        });
    }

}
