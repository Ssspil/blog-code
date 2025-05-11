package com.aoxx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.aoxx")
public class MultimoduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultimoduleApplication.class);
    }
}
