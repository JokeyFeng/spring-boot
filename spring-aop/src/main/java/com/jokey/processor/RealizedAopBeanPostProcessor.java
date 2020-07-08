package com.jokey.processor;

import com.jokey.util.ConfigurationUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * AOP实现核心处理类
 *
 * @author JokeyFeng
 * @date: 2020/7/8
 * @project: spring-boot
 * @package: com.jokey.processor
 * @comment: 针对问题3，我们可以利用BeanPostProcessor，在bean实例化之后，在放入容器之前，进行一个条件过滤，
 * 如果当前对象是我们的目标对象（即在我们定义好的Map中），则对对象进行代理，将目标对象替换成代理对象返回即可.
 * （注：spring实现aop采用cglib和jdk动态代理两种方式，@EnableAspectJAutoProxy(proxyTargetClass=true)可以加开关控制，
 * 如果不加，目标对象如果有实现接口，则使用jdk动态代理，如果没有就采用cglib（因为我们知道cglib是基于继承的））
 * <p>
 * 我们这里实现，都简单粗暴一点，统一采用cglib代理，这样就可以完成对任意对象的代理了。
 */
public class RealizedAopBeanPostProcessor implements BeanPostProcessor {
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		String targetClazz = bean.getClass().getName();
		Object object = bean;
		if (ConfigurationUtil.clazzProxyBeanHolderMap.containsKey(targetClazz)) {
			//cglib代理
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(object.getClass());
			enhancer.setCallback(new CustomizedProxyInterceptor(ConfigurationUtil.clazzProxyBeanHolderMap.get(targetClazz)));
			object = enhancer.create();
		}
		return object;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
