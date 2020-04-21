package com.jokey.bingo.spi.java.impl;

import com.jokey.bingo.spi.java.AnimalService;

/**
 * @author JokeyFeng
 * @date: 2020/4/14
 * @project: spring-boot
 * @package: com.jokey.bingo.spi.dubbo.java.impl
 * @comment:
 */
public class Dog implements AnimalService {
	@Override
	public void category() {
		System.out.println("I am a JAVA Dog......");
		
	}
}
