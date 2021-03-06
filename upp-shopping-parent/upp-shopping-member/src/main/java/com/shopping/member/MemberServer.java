
package com.shopping.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.09.27 16:01
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"com.shopping.common","com.shopping.member"})
public class MemberServer {

    public static void main(String[] args) {
        SpringApplication.run(MemberServer.class, args);
    }

}
