package com.jokey.bingo.spi.dubbo.impl;

import com.jokey.bingo.spi.dubbo.AnimalService;

/**
 * @author JokeyFeng
 * @date: 2020/5/10
 * @project: spring-boot
 * @package: com.jokey.bingo.spi.dubbo.impl
 * @comment:
 */
public class DefaultAnimal implements AnimalService {
	
	@Override
	public void category() {
		System.out.println("我是默认实现类");
	}
}
