package com.jumia.skylens.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jumia.skylens")
public class Application {

    static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
