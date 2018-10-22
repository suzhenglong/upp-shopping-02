package com.shopping.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.09.30 15:26
 */
@SpringBootApplication
@EnableEurekaClient
public class MessagesServer {
    public static void main(String[] args) {
        SpringApplication.run(MessagesServer.class, args);
    }
}
