package com.jokey.bingo.annotation;

import java.lang.annotation.*;

/**
 * @author Zhengjingfeng
 * @created 2019/4/24 9:16
 * @comment
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisEvict {
    Class clazz();
}
