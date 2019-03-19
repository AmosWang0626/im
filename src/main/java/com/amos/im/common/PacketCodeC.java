package com.amos.im.common;

import com.amos.im.request.Command;
import com.amos.im.serializer.Serializer;
import com.amos.im.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * PROJECT: im
 * DESCRIPTION: 通讯协议算法
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
public class PacketCodeC {

    /**
     * 魔数
     */
    private static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encode(BasePacket basePacket) {
        // 1. 创建 ByteBuf 对象
        // ioBuffer() 尽可能返回直接内存(也即不受JVM堆管理的内存空间)
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = SerializerAlgorithm.DEFAULT.serialize(basePacket);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(basePacket.getVersion());
        byteBuf.writeByte(SerializerAlgorithm.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(basePacket.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public BasePacket decode(ByteBuf byteBuf) {
        // 跳过 魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends BasePacket> requestType = Command.getRequestType(command);
        Serializer serializer = SerializerAlgorithm.getSerializer(serializeAlgorithm);

        if (requestType != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

}
