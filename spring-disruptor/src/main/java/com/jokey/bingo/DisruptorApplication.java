package com.jokey.bingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author JokeyFeng
 * @date: 2020/3/16
 * @project: spring-boot
 * @package: PACKAGE_NAME
 * @comment:
 */
@SpringBootApplication
public class DisruptorApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DisruptorApplication.class, args);
		new LongEventMain().run();
	}
}