package com.sanjati.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class CoreApp {
    public static void main(String[] args) {
        log.debug("start");
        SpringApplication.run(CoreApp.class, args);

    }
}
