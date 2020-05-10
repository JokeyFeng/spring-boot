package com.jokey.bingo.red.packet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author JokeyFeng
 */
@EnableAsync
@SpringBootApplication
@MapperScan(basePackages = "com.jokey.bingo.red.packet.mapper")
public class RedPacketApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RedPacketApplication.class, args);
	}
	
}
