package com.jokey.processor;

import com.jokey.holder.ProxyBeanHolder;
import com.jokey.util.ConfigurationUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

/**
 * @author JokeyFeng
 * @date: 2020/7/8
 * @project: spring-boot
 * @package: com.jokey.processor
 * @comment: 定义我们的注册类，用于注册我们的目标对象和通知对象之间的关系，
 * 其核心代码如下，首先实现BeanFactoryPostProcessor ，保证其实在对所有的bean完成扫描后，在bean的实例化之前执行，
 * 然后再其中按上述思路，scan出所有的目标对象，然后建立起目标对象和通知对象的关联关系，然后放入我们的Map中
 */
@Component
public class RegisterBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	
	/**
	 * 存放需要代理的相关信息类
	 */
	public static volatile List<ProxyBeanHolder> proxyBeanHolderList = new Vector<>();
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		//获取所有的beanDefinitionName
		String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
			//判断是否是一个注解beanDefinitionName
			if (beanDefinition instanceof AnnotatedBeanDefinition) {
				//获取beanDefinition的所有注解
				AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();
				Set<String> annotations = metadata.getAnnotationTypes();
				//循环所有注解，找到aop切面注解类
				for (String annotation : annotations) {
					if (Objects.equals(annotation, ConfigurationUtil.AOP_POINTCUT_ANNOTATION)) {
						this.doScan((GenericBeanDefinition) beanDefinition);
					}
				}
			}
		}
	}
	
	/**
	 * 扫描所有注解方法
	 *
	 * @param beanDefinition
	 */
	private void doScan(GenericBeanDefinition beanDefinition) {
		String className = beanDefinition.getBeanClassName();
		try {
			Class<?> beanDefinitionClazz = Class.forName(className);
			Method[] methods = beanDefinitionClazz.getMethods();
			for (Method method : methods) {
				Annotation[] annotations = method.getAnnotations();
				for (Annotation annotation : annotations) {
					String annotationName = annotation.annotationType().getName();
					if (Objects.equals(annotationName, ConfigurationUtil.BEFORE)
							|| Objects.equals(annotationName, ConfigurationUtil.AROUND)
							|| Objects.equals(annotationName, ConfigurationUtil.AFTER)) {
						this.doScan(className, method, annotation);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 扫描出所有被代理的类
	 *
	 * @param className
	 * @param method
	 * @param annotation
	 */
	private void doScan(String className, Method method, Annotation annotation) {
		ProxyBeanHolder proxyBeanHolder = new ProxyBeanHolder();
		proxyBeanHolder.setClassName(className);
		proxyBeanHolder.setMethodName(method.getName());
		proxyBeanHolder.setAnnotationName(annotation.annotationType().getName());
		
		//获取注解上的所有方法
		Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
		String packagePath = null;
		for (Method annotationMethod : annotationMethods) {
			if (Objects.equals("value", annotationMethod.getName())) {
				try {
					packagePath = (String) annotationMethod.invoke(annotation, null);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (!packagePath.isEmpty()) {
			String rootPath = this.getClass().getResource("/").getPath();
			String targetPackagePath = rootPath + packagePath.replace(".", "/");
			File file = new File(targetPackagePath);
			File[] files = file.listFiles();
			List<ProxyBeanHolder> proxyBeanHolders;
			for (File item : files) {
				//判断是否为文件
				if (item.isFile()) {
					String targetClass = packagePath + "." + item.getName().replace(".class", "");
					proxyBeanHolders = ConfigurationUtil.clazzProxyBeanHolderMap.get(targetClass);
					if (proxyBeanHolders == null) {
						proxyBeanHolders = new Vector<>();
					}
					proxyBeanHolders.add(proxyBeanHolder);
					ConfigurationUtil.clazzProxyBeanHolderMap.put(targetClass, proxyBeanHolders);
				}
			}
		}
	}
}
