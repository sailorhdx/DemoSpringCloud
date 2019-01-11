package com.v2x.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer8761standaloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer8761standaloneApplication.class, args);
    }
}
