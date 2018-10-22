package com.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.10.05 21:03
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class PayWebServer {
    public static void main(String[] args) {
        SpringApplication.run(PayWebServer.class, args);
    }
}

