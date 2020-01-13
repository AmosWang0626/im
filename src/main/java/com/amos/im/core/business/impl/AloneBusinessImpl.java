package com.amos.im.core.business.impl;

import com.amos.im.common.GeneralCode;
import com.amos.im.common.GeneralResponse;
import com.amos.im.common.util.PrintUtil;
import com.amos.im.core.business.AloneBusiness;
import com.amos.im.core.command.request.MessageRequest;
import com.amos.im.core.session.ClientSession;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
        fromChannel.writeAndFlush(messageRequest);

        PrintUtil.message(messageRequest.getCreateTime(), "我", messageRequest.getMessage());

        return new GeneralResponse<>(System.currentTimeMillis());
    }

}
