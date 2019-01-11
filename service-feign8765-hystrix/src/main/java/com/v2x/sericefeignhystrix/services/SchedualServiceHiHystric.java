package com.v2x.sericefeignhystrix.services;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "hi," + name + ", Hystrix  say sorry,error!";
    }
}