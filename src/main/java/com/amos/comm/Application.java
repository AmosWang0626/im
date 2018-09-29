package com.amos.comm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

    @GetMapping("/")
    public String info() {
        return "Hello Docker By Gradle!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
