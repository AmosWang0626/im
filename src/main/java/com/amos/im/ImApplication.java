package com.amos.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
import java.util.Scanner;

/**
 * @author amos
 */
@SpringBootApplication
public class ImApplication {

    public static void main(String[] args) {
        /*SpringApplication.run(ImApplication.class, args);*/
        SpringApplication app = new SpringApplication(ImApplication.class);
        app.setDefaultProperties(consoleInit());
        app.run(args);
    }

    /**
     * 设置启动参数
     *
     * @return Properties
     */
    private static Properties consoleInit() {
        Properties properties = new Properties();
        String commandClient = "c";
        String commandServer = "s";
        String commandExit = "exit";

        Scanner scanner = new Scanner(System.in);
        System.out.print("请指定启动项 [客户端 (c); 服务端 (s); 退出 (exit)]: ");
        String environment = scanner.nextLine();

        // 输入字符非法
        boolean inputIllegal = true;
        do {
            if (commandClient.equalsIgnoreCase(environment)) {
                inputIllegal = false;
                properties.setProperty("spring.profiles.active", "client");
            } else if (commandServer.equalsIgnoreCase(environment)) {
                inputIllegal = false;
                properties.setProperty("spring.profiles.active", "server");
            } else if (commandExit.equalsIgnoreCase(environment)) {
                System.out.println("已关闭系统!");
                System.exit(0);
            } else {
                System.out.print("请指定启动项 [客户端 (c); 服务端 (s); 退出 (exit)]: ");
                environment = scanner.nextLine();
            }

        } while (inputIllegal);

        return properties;
    }

}
