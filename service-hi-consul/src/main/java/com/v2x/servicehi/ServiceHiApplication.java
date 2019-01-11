package com.v2x.servicehi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ServiceHiApplication {
    /**
     * 访问地址 http://localhost:8762/actuator/hystrix.stream
     * @param args
     */

    public static void main(String[] args) {
        SpringApplication.run(ServiceHiApplication.class, args);
    }

    @RequestMapping("/hi")
    public String home() {
        return "hi ,i'm miya";
    }



}
