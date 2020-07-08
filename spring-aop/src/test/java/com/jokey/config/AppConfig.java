package com.jokey.config;

import com.jokey.annotation.EnableAop;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zhengjingfeng
 * @created 2020/7/8 16:46
 * @comment
 */
@EnableAop
@Configuration
@ComponentScan("com.jokey")
public class AppConfig {
}
