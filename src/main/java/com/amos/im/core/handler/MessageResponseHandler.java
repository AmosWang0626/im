package com.amos.im.core.handler;

import com.amos.im.common.util.PrintUtil;
import com.amos.im.core.command.response.MessageResponse;
import com.amos.im.dao.cache.ChatRecordCache;
import com.amos.im.dao.entity.ChatRecord;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Daoyuan
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponse msg) {
        PrintUtil.message(msg.getCreateTime(), msg.getUsername(), msg.getMessage());
        ChatRecordCache.instance().save(new ChatRecord().setId(String.valueOf(System.currentTimeMillis()))
                .setChatFlag(false)
                .setSenderId(msg.getSender()).setReceiverId(msg.getReceiver())
                .setMessage(msg.getMessage()).setCreateTime(msg.getCreateTime())
        );
    }

}
