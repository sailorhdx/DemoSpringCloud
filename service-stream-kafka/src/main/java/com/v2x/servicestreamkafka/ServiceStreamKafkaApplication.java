package com.v2x.servicestreamkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceStreamKafkaApplication {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:\\Soft\\hadoop-3.1.1");
        System.setProperty("HADOOP_USER_NAME", "hdfs");

        SpringApplication.run(ServiceStreamKafkaApplication.class, args);
    }
}
