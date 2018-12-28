package com.jokey.bingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author JokeyFeng
 * date:2018/12/5
 * project:spring-boot
 * package:com.jokey.bingo
 * comment:
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
