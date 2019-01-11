package com.v2x.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConfigClientnormal8881Application {

    /**
     * http://localhost:8881/actuator/bus-refresh
     */
//    http://localhost:8881/hi
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientnormal8881Application.class, args);
    }

    @Value("${foo}")
    String foo;

    @RequestMapping(value = "/hi")
    public String hi(){
        return foo;
    }
}
