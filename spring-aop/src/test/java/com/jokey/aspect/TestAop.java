package com.jokey.aspect;

import com.jokey.annotation.AfterMe;
import com.jokey.annotation.AopJ;
import com.jokey.annotation.AroundMe;
import com.jokey.annotation.BeforeMe;
import org.springframework.stereotype.Component;

/**
 * @author JokeyFeng
 * @date: 2020/7/8
 * @project: spring-boot
 * @package: com.jokey.config
 * @comment:
 */
@AopJ
@Component
public class TestAop {

    @BeforeMe("com.jokey.mapper")
    public void testBefore() {
        System.out.println("执行前置通知逻辑.......");
    }

    @AroundMe("com.jokey.mapper")
    public void testAround() {
        System.out.println("执行环绕通知逻辑.......");
    }

    @AfterMe("com.jokey.mapper")
    public void testAfter() {
        System.out.println("执行后置通知逻辑.......");
    }
}
