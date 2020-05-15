package com.jokey.bingo.test;

import com.jokey.bingo.spi.dubbo.AnimalService;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

/**
 * @author JokeyFeng
 * @date: 2020/4/14
 * @project: spring-boot
 * @package: com.jokey.bingo.test
 * @comment:
 */
public class DubboSpiTest {
	
	@Test
	public void test() {
		ExtensionLoader<AnimalService> loader = ExtensionLoader.getExtensionLoader(AnimalService.class);
		AnimalService sheep = loader.getExtension("sheep");
		sheep.category();
		AnimalService pig = loader.getExtension("pig");
		pig.category();
		loader.getAdaptiveExtension().category();
	}
}
