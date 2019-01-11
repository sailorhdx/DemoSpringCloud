package com.v2x.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer8769haApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer8769haApplication.class, args);
    }
}
