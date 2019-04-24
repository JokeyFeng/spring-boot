package com.jokey.bingo.annotation;

import java.lang.annotation.*;

/**
 * @author Zhengjingfeng
 * @created 2019/4/24 9:13
 * @comment 添加缓存注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    Class clazz();
}
