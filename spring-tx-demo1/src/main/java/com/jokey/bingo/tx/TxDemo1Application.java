/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2018/12/29</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.bingo.tx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.tx
 * @ClassName TxDemo1Application
 * @Author JokeyZheng
 * @Date 2018/12/29
 * @Version 1.0
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class TxDemo1Application {
    public static void main(String[] args) {
        SpringApplication.run(TxDemo1Application.class, args);
    }
}
