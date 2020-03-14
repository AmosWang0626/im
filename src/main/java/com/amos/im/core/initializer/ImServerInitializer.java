package com.amos.im.core.initializer;

import com.amos.im.core.handler.ImHandler;
import com.amos.im.core.handler.LoginRequestHandler;
import com.amos.im.core.handler.WebsocketParseHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * DESCRIPTION: ImServerInitializer
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/3/14
 */
public class ImServerInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /* 对 Http 进行支持 */
        // 前端使用Websocket时，需要用到Http编解码器
        pipeline.addLast(new HttpServerCodec());
        // support for writing a large data stream
        pipeline.addLast(new ChunkedWriteHandler());
        // 对HttpMessage进行聚合，聚合成FullHttpRequest或者FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(1024 * 4));

        /* 对 Websocket 进行支持 */
        // 简化 Websocket 操作；handshaking as well as processing of control frames (Close, Ping, Pong)
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new WebsocketParseHandler());

        /// 自定义协议
        // ch.pipeline().addLast(new PacketSplitter());
        // ch.pipeline().addLast(PacketCodec.INSTANCE);

        pipeline.addLast(LoginRequestHandler.INSTANCE);
        pipeline.addLast(ImHandler.INSTANCE);
    }

}
