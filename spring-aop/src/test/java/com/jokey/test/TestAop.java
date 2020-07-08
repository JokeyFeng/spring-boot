package com.jokey.test;

import com.jokey.config.AppConfig;
import com.jokey.mapper.TestMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Zhengjingfeng
 * @created 2020/7/8 16:47
 * @comment
 */
public class TestAop {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        applicationContext.getBean(TestMapper.class).test01();
        applicationContext.getBean(TestMapper.class).test02();
    }
}
