package com.amos.im.common.serializer;

import com.alibaba.fastjson.JSON;

/**
 * PROJECT: im
 * DESCRIPTION: 序列化---Json
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
public class ProtoBufSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.PROTOBUF;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
