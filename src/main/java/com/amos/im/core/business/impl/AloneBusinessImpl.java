package com.amos.im.core.business.impl;

import com.amos.im.common.GeneralCode;
import com.amos.im.common.GeneralResponse;
import com.amos.im.common.util.PrintUtil;
import com.amos.im.core.business.AloneBusiness;
import com.amos.im.core.pojo.form.MessageRecordRequest;
import com.amos.im.core.command.request.MessageRequest;
import com.amos.im.core.session.ClientSession;
import com.amos.im.dao.cache.ChatRecordCache;
import com.amos.im.dao.entity.ChatRecord;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DESCRIPTION: alone chat
 *
 * @author amos
 * @date 2020-01-12
 */
@Component("aloneBusiness")
public class AloneBusinessImpl implements AloneBusiness {


    @Override
    public GeneralResponse<?> alone(MessageRequest messageRequest) {
        Channel fromChannel = ClientSession.getChannel(messageRequest.getSender());
        if (fromChannel == null) {
            return new GeneralResponse<>(GeneralCode.ALONE_BOTH_LOGIN);
        }
        messageRequest.setCreateTime(LocalDateTime.now());

        // chat record
        ChatRecord chatRecord = new ChatRecord().setId(String.valueOf(System.currentTimeMillis()))
                .setChatFlag(true)
                .setSenderId(messageRequest.getSender()).setReceiverId(messageRequest.getReceiver())
                .setMessage(messageRequest.getMessage()).setCreateTime(messageRequest.getCreateTime());
        ChatRecordCache.instance().save(chatRecord);
        PrintUtil.message(messageRequest.getCreateTime(), "我", messageRequest.getMessage());

        fromChannel.writeAndFlush(messageRequest);

        return new GeneralResponse<>(chatRecord);
    }

    @Override
    public GeneralResponse<List<ChatRecord>> list(MessageRecordRequest request) {
        List<ChatRecord> values = ChatRecordCache.instance().values(
                new ChatRecord().setSenderId(request.getSender()).setReceiverId(request.getReceiver())
        );
        return new GeneralResponse<>(values);
    }

}
