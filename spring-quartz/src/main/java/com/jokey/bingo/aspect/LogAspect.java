package com.jokey.bingo.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.jokey.base.annotation.Log;
import com.jokey.base.properties.BingoProperties;
import com.jokey.base.util.AddressUtils;
import com.jokey.base.util.HttpServletUtils;
import com.jokey.base.util.IpUtils;
import com.jokey.bingo.entity.SysLog;
import com.jokey.bingo.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 记录用户操作日志
 *
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.aspect
 * comment:
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    @Autowired
    private BingoProperties bingoProperties;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("@annotation(com.jokey.base.annotation.Log)")
    public void pointcut() {
        // nothing to do
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Exception {
        Object result = null;
        long startTime = System.currentTimeMillis();

        try {
            //执行方法
            result = point.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
        //获取Request
        HttpServletRequest request = HttpServletUtils.getHttpServletRequest();
        //获取IP地址
        String ip = IpUtils.getIpAddress(request);
        if (bingoProperties.isOpenAopLog()) {
            long time = System.currentTimeMillis() - startTime;
            this.saveLog(point, time, ip);
        }
        return result;
    }

    @Async
    public void saveLog(ProceedingJoinPoint joinPoint, long time, String ip) throws IOException {
        SysLog sysLog = new SysLog();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //获取Log注解
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            //获取注解上的描述
            sysLog.setOperation(logAnnotation.value());
        }

        //请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //请求的方法名
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        //请求的参数值
        Object[] args = joinPoint.getArgs();
        //请求方法的参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder builder = new StringBuilder();
            builder = this.handleParams(builder, args, Arrays.asList(paramNames));
            sysLog.setParams(builder.toString());
        }
        //设置IP地址
        sysLog.setIp(ip);
        //todo sysLog.setUsername("")
        sysLog.setTime(time);
        sysLog.setCreateTime(new Date());
        sysLog.setLocation(AddressUtils.getCityInfo(sysLog.getIp()));
        //保存日志
        this.sysLogService.save(sysLog);

    }

    private StringBuilder handleParams(StringBuilder builder, Object[] args, List paramNames) throws IOException {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map) {
                Set set = ((Map) args[i]).keySet();
                List list = Lists.newArrayList();
                List paramList = Lists.newArrayList();
                for (Object key : set) {
                    list.add(((Map) args[i]).get(key));
                    paramList.add(key);
                }
                return handleParams(builder, list.toArray(), paramNames);
            } else {
                if (args[i] instanceof Serializable) {
                    Class<?> aClass = args[i].getClass();

                    try {
                        aClass.getDeclaredMethod("toString", new Class[]{null});
                        // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                        builder.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                    } catch (NoSuchMethodException e) {
                        builder.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                    }
                } else if (args[i] instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) args[i];
                    builder.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
                } else {
                    builder.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
                }
            }
        }
        return builder;
    }
}
