package com.amos.im.common.protocol;

import com.amos.im.common.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * PROJECT: im
 * DESCRIPTION: 简化编码和解码
 *
 * @author amos
 * @date 2019/4/4
 * <p>
 * 等于合并了下边两个类
 * @see PacketEncoder
 * @see PacketDecoder
 */
@ChannelHandler.Sharable
public class PacketCodec extends MessageToMessageCodec<ByteBuf, BasePacket> {

    public static final PacketCodec INSTANCE = new PacketCodec();

    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket basePacket, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        PacketProtocol.getInstance().encode(byteBuf, basePacket);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(PacketProtocol.getInstance().decode(msg));
    }

}
