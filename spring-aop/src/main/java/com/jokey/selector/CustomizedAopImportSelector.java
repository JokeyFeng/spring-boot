package com.jokey.selector;

import com.jokey.processor.RealizedAopBeanPostProcessor;
import com.jokey.processor.RegisterBeanFactoryPostProcessor;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义AOP实现，提交给spring处理的类
 *
 * @author JokeyFeng
 * @date: 2020/7/8
 * @project: spring-boot
 * @package: com.jokey.selector
 * @comment:
 */
public class CustomizedAopImportSelector implements ImportSelector {
	
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{RealizedAopBeanPostProcessor.class.getName(), RegisterBeanFactoryPostProcessor.class.getName()};
	}
}
