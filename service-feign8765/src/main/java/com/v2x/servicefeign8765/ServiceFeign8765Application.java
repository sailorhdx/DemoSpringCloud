package com.v2x.servicefeign8765;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceFeign8765Application {
//    http://localhost:8765/hi?name=forezp
    public static void main(String[] args) {
        SpringApplication.run(ServiceFeign8765Application.class, args);
    }
}
