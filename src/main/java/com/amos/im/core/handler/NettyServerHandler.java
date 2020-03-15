package com.amos.im.core.handler;

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
public class NettyServerHandler extends ChannelInitializer<NioSocketChannel> {


    /**
     * 前端要想建立 Websocket 连接，第一次握手请求消息由 HTTP 协议承载，所以它是一个 HTTP 消息。
     * 正常情况第一次握手，消息头中会包含 Upgrade 字段。
     * HttpServerCodec       [request]将请求和应答消息编码或者解码为 HTTP 消息，生成多个消息对象
     * HttpObjectAggregator  [request]将多个消息对象转换成单一的 FullHttpRequest 或者 FullHttpResponse
     * ChunkedWriteHandler   [response]处理异步发送大的码流的
     */
    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024 * 4));
        pipeline.addLast(new ChunkedWriteHandler());

        // 简化 Websocket 操作；handshaking as well as processing of control frames (Close, Ping, Pong)
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new WebsocketHandler());

        /// 自定义协议
        // ch.pipeline().addLast(new PacketSplitter());
        // ch.pipeline().addLast(PacketCodec.INSTANCE);

        pipeline.addLast(LoginRequestHandler.INSTANCE);
        pipeline.addLast(ImHandler.INSTANCE);
    }

}
