package com.shopping.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

 /**
  * @Description:(Spring Cloud服务注册中心)
  * @author: zhenglongsu@163.com
  * @date: 2018.09.29 14:55
  */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {
	public static void main(String[] args) {
       SpringApplication.run(EurekaServer.class, args);
	}
}
