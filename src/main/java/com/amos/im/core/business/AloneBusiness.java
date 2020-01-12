package com.amos.im.core.business;

import com.amos.im.common.GeneralResponse;
import com.amos.im.core.command.request.MessageRequest;

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
    GeneralResponse alone(MessageRequest messageRequest);


}
