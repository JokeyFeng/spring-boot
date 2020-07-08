package com.jokey.util;

import com.jokey.holder.ProxyBeanHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JokeyFeng
 * @date: 2020/7/7
 * @project: spring-boot
 * @package: com.jokey.util
 * @comment:
 */
public class ConfigurationUtil {
	
	/**
	 * aop标识注解类
	 */
	public static final String AOP_POINTCUT_ANNOTATION = "com.jokey.annotation.AopJ";
	
	/**
	 * 前置通知注解类
	 */
	public static final String BEFORE = "com.jokey.annotation.BeforeMe";
	
	/**
	 * 环绕通知注解类
	 */
	public static final String AROUND = "com.jokey.annotation.AroundMe";
	
	/**
	 * 后置通知注解类
	 */
	public static final String AFTER = "com.jokey.annotation.AfterMe";
	
	/**
	 * 存放需代理的全部目标对象类
	 */
	public static volatile Map<String, List<ProxyBeanHolder>> clazzProxyBeanHolderMap = new ConcurrentHashMap<>();
}
