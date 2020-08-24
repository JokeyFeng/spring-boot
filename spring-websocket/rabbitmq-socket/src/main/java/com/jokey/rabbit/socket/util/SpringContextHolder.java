package com.jokey.rabbit.socket.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author JokeyFeng
 * @date: 2020/8/18
 * @project: spring-boot
 * @package: com.jokey.rabbit.socket.util
 * @comment:
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
	
	private static ConfigurableApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		applicationContext = (ConfigurableApplicationContext) applicationContext;
	}
	
	/**
	 * 获取ApplicationContext对象
	 *
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * 停止应用程序
	 */
	public static void colse() {
		if (applicationContext != null) {
			applicationContext.close();
		}
	}
	
	/**
	 * 根据bean的名称获取bean
	 *
	 * @param name
	 * @return
	 */
	public static Object getBeanByName(String name) {
		return applicationContext.getBean(name);
	}
	
	/**
	 * 根据bean的class来查找对象
	 *
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T getBeanByClass(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	/**
	 * 根据bean的class来查找所有的对象(包括子类)
	 *
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, T> getBeansByClass(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz);
	}
	
	/**
	 * 获取HttpServletRequest
	 *
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attributes.getRequest();
	}
	
	/**
	 * 获取HttpSession
	 *
	 * @return
	 */
	public static HttpSession getHttpSession() {
		return getHttpServletRequest().getSession();
	}
	
	/**
	 * 获取完整的请求URL
	 */
	public static String getRequestUrl() {
		HttpServletRequest request = getHttpServletRequest();
		//当前请求路径
		String currentUrl = request.getRequestURL().toString();
		//请求参数
		String queryString = request.getQueryString();
		if (!StringUtils.isEmpty(queryString)) {
			currentUrl = currentUrl + "?" + queryString;
		}
		String result = "";
		try {
			result = URLEncoder.encode(currentUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取请求的客户端IP
	 */
	public static String getRequestIp() {
		HttpServletRequest request = getHttpServletRequest();
		String ip = request.getHeader("X-Forwarded-For");
		String unKnown = "unKnown";
		if (StringUtils.hasText(ip) && !unKnown.equalsIgnoreCase(ip)) {
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.hasText(ip) && !unKnown.equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
