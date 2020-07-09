package com.amos.im.main;

import com.alibaba.fastjson.JSON;
import com.amos.im.core.command.request.MessageRequest;

import java.time.LocalDateTime;

/**
 * DESCRIPTION: JsonTest
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/7/9
 */
public class JsonTest {

    /**
     * {"command":3,"createTime":"2020-07-09T21:29:26.392","message":"这里是FBI，请配合调查","receiver":"amos","sender":"wang","version":1}
     * {"command":3,"createTime":"2020-07-09T21:29:26.392","message":"你们是","receiver":"wang","sender":"amos","version":1}
     */
    public static void main(String[] args) {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessage("这里是FBI，请配合调查");
        messageRequest.setReceiver("amos");
        messageRequest.setSender("wang");
        messageRequest.setCreateTime(LocalDateTime.now());

        System.out.println(JSON.toJSONString(messageRequest));
    }

}
