package com.amos.im.io;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Date;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/1/16
 */
public class IOClientMain {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                while (true) {
                    Socket socket = new Socket("127.0.0.1", 8888);
                    int i = 5;
                    do {
                        try {
                            socket.getOutputStream().write((LocalTime.now() + ": hello world!").getBytes());
                            System.out.println("send: " + new Date() + ": hello world!");
                            Thread.sleep(2000);
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    } while (--i != 0);
                }
            } catch (Exception ignored) {
            }
        }).start();
    }

}
