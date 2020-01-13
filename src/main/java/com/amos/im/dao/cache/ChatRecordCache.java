package com.amos.im.dao.cache;

import com.amos.im.common.util.RedisUtil;
import com.amos.im.dao.api.RedisCache;
import com.amos.im.dao.entity.ChatRecord;

import java.util.List;

/**
 * DESCRIPTION: 聊天记录存储
 *
 * @author <a href="mailto:amos.wang@xiaoi.com">amos.wang</a>
 * @date 1/13/2020
 */
public class ChatRecordCache implements RedisCache<ChatRecord> {

    @Override
    public String key() {
        return "CHAT_RECORD_";
    }

    @Override
    public Integer index() {
        return 9;
    }

    @Override
    public void save(ChatRecord data) {
        String key = key() + data.getSenderId() + "_" + data.getReceiver();
        String value = RedisUtil.get(key, index());
        
    }

    @Override
    public void delete(ChatRecord data) {

    }

    @Override
    public ChatRecord value(Integer index) {
        return null;
    }

    @Override
    public List<ChatRecord> values(Integer index) {
        return null;
    }

}
