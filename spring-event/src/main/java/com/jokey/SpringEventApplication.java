package com.jokey;

import com.jokey.publisher.UserRegisterPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JokeyFeng
 * @date: 2020/8/25
 * @project: spring-boot
 * @package: com.jokey
 * @comment:
 */
@RestController
@SpringBootApplication
public class SpringEventApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringEventApplication.class, args);
	}
	
	@Autowired
	private UserRegisterPublisher userRegisterPublisher;
	
	@PostMapping("/register")
	public String register(@RequestParam String name) {
		userRegisterPublisher.register(name);
		return "success";
	}
}
