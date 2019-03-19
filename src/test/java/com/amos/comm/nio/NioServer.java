package com.amos.comm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/1/18
 */
public class NioServer {

    /*
     * note: channel register
     * <p>
     * SelectionKey.OP_READ
     * 对应 00000001，通道中有数据可以进行读取
     * <p>
     * SelectionKey.OP_WRITE
     * 对应 00000100，可以往通道中写入数据
     * <p>
     * SelectionKey.OP_CONNECT
     * 对应 00001000，成功建立 TCP 连接
     * <p>
     * SelectionKey.OP_ACCEPT
     * 对应 00010000，接受 TCP 连接
     */

    public static void main(String[] args) throws IOException {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();

        new Thread(() -> {
            try {
                ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                listenerChannel.socket().bind(new InetSocketAddress(8888));
                // false 非阻塞；true 阻塞。
                listenerChannel.configureBlocking(false);
                listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                while (true) {
                    // 检测是否有新的连接，阻塞时间 1 毫秒
                    if (serverSelector.select(1L) > 0) {
                        Set<SelectionKey> set = serverSelector.selectedKeys();
                        if (set.size() == 0) {
                            continue;
                        }
                        set.forEach(selectionKey -> {
                            if (selectionKey.isAcceptable()) {
                                try {
                                    SocketChannel clientChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                } catch (Exception ignore) {
                                } finally {
                                    set.remove(selectionKey);
                                }
                            }
                        });
                    }
                }
            } catch (Exception ignore) {
            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    // 批量轮询有哪些数据可读，阻塞时间 1 毫秒
                    if (clientSelector.select(1L) > 0) {
                        Set<SelectionKey> selectionKeys = clientSelector.selectedKeys();
                        if (selectionKeys.size() == 0) {
                            continue;
                        }
                        selectionKeys.forEach(selectionKey -> {
                            try {
                                if (selectionKey.isReadable()) {
                                    SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    clientChannel.read(byteBuffer);
                                    byteBuffer.flip();
                                    System.out.println("read: " + Charset.defaultCharset().newDecoder().decode(byteBuffer));
                                }
                            } catch (Exception ignored) {
                            } finally {
                                selectionKeys.remove(selectionKey);
                                selectionKey.interestOps(SelectionKey.OP_READ);
                            }
                        });
                    }
                }
            } catch (Exception ignored) {
            }

        }).start();

    }

}
