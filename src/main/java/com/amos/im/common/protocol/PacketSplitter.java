package com.amos.im.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/21
 */
public class PacketSplitter extends LengthFieldBasedFrameDecoder {

    /*
     * 魔数 + 版本号 + 序列化算法 + 指令 + Max数据长度 + 数据
     * int + byte + byte + byte + int + byte[]
     * 4 + 1 + 1 + 1 + 4 + N (unit: byte)
     */

    /**
     * 魔数 + 版本号 + 序列化算法 + 指令
     * 4 + 1 + 1 + 1 = 7
     */
    private static final int PROTOCOL_OFFSET = 7;
    /**
     * 数据长度
     */
    private static final int PROTOCOL_LENGTH = 4;
    /**
     * 数据最大字节数
     */
    private static final int PROTOCOL_MAX_LENGTH = Integer.MAX_VALUE;

    public PacketSplitter() {
        super(PROTOCOL_MAX_LENGTH, PROTOCOL_OFFSET, PROTOCOL_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (PacketProtocol.MAGIC_NUMBER != in.getInt(in.readerIndex())) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }

//    public static void main(String[] args) {
//        // Integer(MAX: 2147483647; MIN: -2147483648)
//        // 第一位为符号位, 所以不是 [2 ^ (4 * 8)], 而是 [2 ^ (4 * 8 - 1)]
//        System.out.println(Integer.MAX_VALUE);
//        System.out.println(Integer.MAX_VALUE + 1 + "");
//    }
}
