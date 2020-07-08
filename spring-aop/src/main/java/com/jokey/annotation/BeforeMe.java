package com.jokey.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Zhengjingfeng
 * @created 2020/7/7 18:12
 * @comment
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeMe {
	
	String value() default "";
}
