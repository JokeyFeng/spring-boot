package com.codingapi.tm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author JokeyFeng
 * date:2018/12/5
 * project:spring-boot
 * package:com.codingapi.tm
 * comment:
 */
@EnableEurekaClient
@SpringBootApplication
public class TxManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TxManagerApplication.class, args);
    }
}
