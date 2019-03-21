package com.amos.im.common.protocol;

import com.amos.im.common.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Daoyuan
 */
public class PacketEncoder extends MessageToByteEncoder<BasePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket msg, ByteBuf out) throws Exception {
        PacketProtocol.getInstance().encode(out, msg);
    }

}
