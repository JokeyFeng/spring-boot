package com.jokey.bingo.aspectj;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.jokey.bingo.annotation.RedisCache;
import com.jokey.bingo.annotation.RedisEvict;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Zhengjingfeng
 * @created 2019/4/24 9:21
 * @comment
 */

@Aspect
@Component
public class RedisCacheAspect {

    private static Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Around("execution(* com.jokey.bingo..jpa..*Repository.select*(..))" +
            "|| execution(* com.jokey.bingo..jpa..*Repository.get*(..))" +
            "|| execution(* com.jokey.bingo..jpa..*Repository.find*(..))")
    public Object redisCache(ProceedingJoinPoint point) throws Throwable {
        //获取类名、方法名、参数
        String clazzType = point.getTarget().getClass().getTypeName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();

        //获取被代理的方法和方法上的注解
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        RedisCache redisCache = method.getAnnotation(RedisCache.class);
        if (redisCache != null) {
            //获取值类型
            Class modelType = redisCache.clazz();
            //根据类名、方法名、参数生成key
            String cacheKey = this.generateKey(modelType.getName(), methodName, args);
            logger.info("缓存的key => {}", cacheKey);
            //检查redis中是否有缓存
            Object result;
            String cacheValue = (String) redisTemplate.opsForHash().get(modelType.getName(), cacheKey);
            if (cacheValue == null) {
                logger.info("缓存没有命中 => {}", cacheKey);
                //查询数据库
                result = point.proceed(args);
                //将查询结果序列化后放进缓存中
                String jsonValue = this.serialize(result);
                redisTemplate.opsForHash().put(modelType.getName(), cacheKey, jsonValue);
            } else {
                logger.info("缓存命中，cacheKey =>{}，value =>{}", cacheKey, cacheValue);
                //获取被代理方法的返回值
                Class returnType = ((MethodSignature) point.getSignature()).getReturnType();
                result = this.deserialize(cacheValue, returnType, modelType);
            }
            return result;
        }

        return point.proceed(args);
    }


    @Around("execution(* com.jokey.bingo..jpa..*Repository.insert*(..))" +
            "|| execution(* com.jokey.bingo..jpa..*Repository.save*(..))" +
            "|| execution(* com.jokey.bingo..jpa..*Repository.update*(..))" +
            "|| execution(* com.jokey.bingo..jpa..*Repository.delete*(..))")
    public Object evictCache(ProceedingJoinPoint point) throws Throwable {
        // 得到被代理的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        // 得到被代理的方法上的注解
        RedisEvict redisEvict = method.getAnnotation(RedisEvict.class);
        if (redisEvict != null) {
            Class modelType = redisEvict.clazz();
            logger.info("清空缓存:{}", modelType.getName());
            // 清除对应缓存
            //redisTemplate.delete(modelType.getName());
            //应该根据key来删除缓存，而不是清空所有缓存
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(modelType.getName());
            entries.forEach((key, value) -> System.out.println(key + "==>" + value));
        }
        return point.proceed(point.getArgs());
    }

    /**
     * 构造缓存的key
     *
     * @param clazzName
     * @param methodName
     * @param args
     * @return
     */
    private String generateKey(String clazzName, String methodName, Object[] args) {
        return clazzName + "::" + methodName + ":" + Joiner.on("&").skipNulls().join(args);
    }

    /**
     * 将数据库查询结果序列化
     */
    protected String serialize(Object target) {
        return JSON.toJSONString(target);
    }

    /**
     * 将redis缓存查询结果反序列化
     */
    protected Object deserialize(String jsonString, Class clazz, Class modelType) {
        // 序列化结果应该是List对象
        if (List.class.isAssignableFrom(clazz)) {
            return JSON.parseArray(jsonString, modelType);
        }

        // 序列化结果是普通对象
        return JSON.parseObject(jsonString, clazz);
    }
}
