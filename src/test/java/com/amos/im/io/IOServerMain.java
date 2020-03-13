package com.amos.im.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/1/16
 */
public class IOServerMain {

    public static void main(String[] args) throws IOException {
        Executor executor = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(8888);
        new Thread(() -> {
            while (true) {
                try {
                    final Socket socket = serverSocket.accept();
                    executor.execute(() -> handler(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void handler(Socket socket) {
        try {
            System.out.printf("线程信息: ID: %s, Name: %s\n", Thread.currentThread().getId(), Thread.currentThread().getName());
            while (true) {
                InputStream is = socket.getInputStream();
                int available;
                do {
                    available = is.available();
                } while (available <= 0);
                byte[] data = new byte[available];
                int read = is.read(data);
                System.out.printf("read[%d]: %s\n", read, new String(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
