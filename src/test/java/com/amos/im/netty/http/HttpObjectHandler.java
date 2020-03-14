package com.amos.im.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * DESCRIPTION: HttpObjectHandler
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/3/14
 */
public class HttpObjectHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (!(msg instanceof HttpRequest)) {
            return;
        }

        HttpRequest request = (HttpRequest) msg;
        System.out.printf("[Server] HttpRequest method [%s], url [%s]\n", request.method(), request.uri());

        System.out.println(new Date() + "：Server 服务器返回数据……");
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes("Hello, Netty~~".getBytes(StandardCharsets.UTF_8));

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

        ctx.channel().writeAndFlush(response);
    }

}
