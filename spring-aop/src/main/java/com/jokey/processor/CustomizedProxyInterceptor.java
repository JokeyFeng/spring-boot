package com.jokey.processor;

import com.jokey.holder.ProxyBeanHolder;
import com.jokey.util.ConfigurationUtil;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author JokeyFeng
 * @date: 2020/7/8
 * @project: spring-boot
 * @package: com.jokey.processor
 * @comment:
 */
public class CustomizedProxyInterceptor implements MethodInterceptor {
	/**
	 * 用于接收切面信息
	 */
	private List<ProxyBeanHolder> proxyBeanHolderList;
	
	public CustomizedProxyInterceptor(List<ProxyBeanHolder> proxyBeanHolderList) {
		this.proxyBeanHolderList = proxyBeanHolderList;
	}
	
	
	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		//处理前置及环绕通知
		for (ProxyBeanHolder proxyBeanHolder : proxyBeanHolderList) {
			String annotationName = proxyBeanHolder.getAnnotationName();
			if (Objects.equals(annotationName, ConfigurationUtil.BEFORE)
					|| Objects.equals(annotationName, ConfigurationUtil.AROUND)) {
				this.doProxy(proxyBeanHolder);
			}
		}
		Object result = methodProxy.invokeSuper(o, objects);
		//处理后置及环绕通知
		for (ProxyBeanHolder proxyBeanHolder : proxyBeanHolderList) {
			String annotationName = proxyBeanHolder.getAnnotationName();
			if (Objects.equals(annotationName, ConfigurationUtil.AFTER)
					|| Objects.equals(annotationName, ConfigurationUtil.AROUND)) {
				this.doProxy(proxyBeanHolder);
			}
		}
		
		return result;
	}
	
	/**
	 * 处理代理操作
	 *
	 * @param proxyBeanHolder
	 */
	private void doProxy(ProxyBeanHolder proxyBeanHolder) {
		String className = proxyBeanHolder.getClassName();
		String methodName = proxyBeanHolder.getMethodName();
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
			Method[] methods = clazz.getMethods();
			for (Method proxyMethod : methods) {
				if (proxyMethod.getName().equals(methodName)) {
					proxyMethod.invoke(clazz.newInstance(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
