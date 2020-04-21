package com.jokey.bingo.spi.dubbo.impl;

import com.jokey.bingo.spi.dubbo.AnimalService;

/**
 * @author JokeyFeng
 * @date: 2020/4/14
 * @project: spring-boot
 * @package: com.jokey.bingo.spi.dubbo.impl
 * @comment:
 */
public class Sheep implements AnimalService {
	
	@Override
	public void category() {
		System.out.println("I am a DUBBO Sheep......");
	}
}
