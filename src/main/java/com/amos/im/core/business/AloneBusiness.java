package com.amos.im.core.business;

import com.amos.im.common.GeneralResponse;
import com.amos.im.core.command.request.MessageRequest;
import com.amos.im.core.pojo.form.MessageRecordRequest;
import com.amos.im.dao.entity.ChatRecord;

import java.util.List;

/**
 * DESCRIPTION: 单聊
 *
 * @author amos
 * @date 2020-01-12
 */
public interface AloneBusiness {

    /**
     * 单聊
     *
     * @param messageRequest 消息表单
     * @return result
     */
    GeneralResponse<?> alone(MessageRequest messageRequest);

    /**
     * 聊天记录
     *
     * @param request MessageRecordRequest
     * @return list
     */
    GeneralResponse<List<ChatRecord>> list(MessageRecordRequest request);

    /**
     * websocket 单聊
     *
     * @param messageRequest 消息表单
     */
    void websocketAlone(MessageRequest messageRequest);

}
