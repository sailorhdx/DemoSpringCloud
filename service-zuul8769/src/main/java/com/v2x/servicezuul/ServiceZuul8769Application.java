package com.v2x.servicezuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class ServiceZuul8769Application {
//    http://localhost:8769/api-a/hi?name=forezp&token=22
//    http://localhost:8769/api-b/hi?name=forezp&token=22
    public static void main(String[] args) {
        SpringApplication.run(ServiceZuul8769Application.class, args);
    }
}
