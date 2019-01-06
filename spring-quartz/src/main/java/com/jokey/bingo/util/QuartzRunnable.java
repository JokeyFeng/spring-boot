package com.jokey.bingo.util;

import com.jokey.base.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.util
 * comment:
 */
@Slf4j
public class QuartzRunnable implements Runnable {

    private Object target;

    private Method method;

    private String params;

    public QuartzRunnable(String beanName, String methodName, String params) throws NoSuchMethodException {
        this.target = SpringContextUtils.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(this.method);
            if (StringUtils.isNotBlank(this.params)) {
                this.method.invoke(target, params);
            } else {
                this.method.invoke(this.target);
            }
        } catch (Exception e) {
            log.error("执行定时任务失败-->{}", e);
        }
    }
}
