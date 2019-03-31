package com.jokey.bingo.auth;

import java.lang.annotation.*;

/**
 * @author JokeyFeng
 * date:2019/3/31
 * project:spring-boot
 * package:com.jokey.bingo
 * comment: 认证注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableAuth {
}
