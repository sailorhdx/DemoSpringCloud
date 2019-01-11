package com.v2x.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerha8769Application {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerha8769Application.class, args);
    }
}
