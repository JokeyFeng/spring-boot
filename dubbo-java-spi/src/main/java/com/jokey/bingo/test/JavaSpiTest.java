package com.jokey.bingo.test;

import com.jokey.bingo.spi.java.AnimalService;
import org.junit.Test;

import java.util.ServiceLoader;

/**
 * @author JokeyFeng
 * @date: 2020/4/14
 * @project: spring-boot
 * @package: com.jokey.bingo.test
 * @comment:
 */
public class JavaSpiTest {
	
	@Test
	public void javaPI() {
		ServiceLoader<AnimalService> services = ServiceLoader.load(AnimalService.class);
		//遍历在配置文件中已配置的AnimalService的所有实现类
		for (AnimalService service : services) {
			service.category();
		}
	}
}
