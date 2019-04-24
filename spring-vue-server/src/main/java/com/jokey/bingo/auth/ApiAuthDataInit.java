package com.jokey.bingo.auth;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JokeyFeng
 * date:2019/3/31
 * project:spring-boot
 * package:com.jokey.bingo
 * comment: API验证数据初始化
 */
@Component
@Configuration
public class ApiAuthDataInit implements ApplicationContextAware {

    public static List<String> checkApis = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> beanMap = ctx.getBeansWithAnnotation(RestController.class);
        for (Object bean : beanMap.values()) {
            Class<?> clazz = bean.getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(EnableAuth.class)) {
                    String apiUrl = this.getApiUrl(clazz, method);
                    System.err.print(apiUrl);
                    checkApis.add(apiUrl);
                }
            }
        }

    }

    private String getApiUrl(Class<?> clazz, Method method) {
        StringBuilder uri = new StringBuilder();
        uri.append(clazz.getAnnotation(RequestMapping.class).value()[0]);

        if (method.isAnnotationPresent(GetMapping.class)) {
            uri.append(method.getAnnotation(GetMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            uri.append(method.getAnnotation(PostMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            uri.append(method.getAnnotation(PutMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            uri.append(method.getAnnotation(DeleteMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            uri.append(method.getAnnotation(RequestMapping.class).value()[0]);
        }
        return uri.toString();
    }
}
