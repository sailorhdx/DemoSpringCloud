package com.v2x.springbootoauth2resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootOauth2ResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOauth2ResourceServerApplication.class, args);
    }

    @RequestMapping(value = "/api")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String success() {
        return "SUCCESS";
    }
}
