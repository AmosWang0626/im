package com.amos.im.dao.cache;

import com.alibaba.fastjson.JSON;
import com.amos.im.common.util.RedisUtil;
import com.amos.im.dao.api.RedisCache;
import com.amos.im.dao.entity.ChatRecord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DESCRIPTION: 聊天记录存储
 *
 * @author <a href="mailto:amos.wang@xiaoi.com">amos.wang</a>
 * @date 1/13/2020
 */
public class ChatRecordCache implements RedisCache<ChatRecord> {

    private static ChatRecordCache INSTANCE;

    public static ChatRecordCache instance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatRecordCache();
        }
        return INSTANCE;
    }

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
        String key = key() + data.getSenderId() + "_" + data.getReceiverId();
        if (!data.getChatFlag()) {
            key = key() + data.getReceiverId() + "_" + data.getSenderId();
        }
        RedisUtil.zadd(key, System.currentTimeMillis(), JSON.toJSONString(data), index());
    }

    @Override
    public void delete(ChatRecord data) {
        String key = key() + data.getSenderId() + "_" + data.getReceiverId();
        RedisUtil.zrem(key, index(), JSON.toJSONString(data));
    }

    @Override
    public ChatRecord value(ChatRecord data) {
        return null;
    }

    @Override
    public List<ChatRecord> values(ChatRecord data) {
        String key = key() + data.getSenderId() + "_" + data.getReceiverId();
        Set<String> set = RedisUtil.zrange(key, index(), -10, -1);
        return set.stream().map(str -> JSON.parseObject(str, ChatRecord.class)).collect(Collectors.toList());
    }

}
