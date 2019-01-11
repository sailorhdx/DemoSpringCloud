package com.v2x.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServernormal8888Application {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServernormal8888Application.class, args);
    }
}