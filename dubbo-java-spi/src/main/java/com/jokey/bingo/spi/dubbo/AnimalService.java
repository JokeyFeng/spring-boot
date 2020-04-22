package com.jokey.bingo.spi.dubbo;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author JokeyFeng
 * @date: 2020/4/14
 * @project: spring-boot
 * @package: com.jokey.bingo.spi.dubbo
 * @comment:
 */
@SPI
public interface AnimalService {
	
	/**
	 * 动物类目
	 */
	void category();
}
