package com.jokey.bingo.delay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author JokeyFeng
 * @date: 2020/3/15
 * @project: spring-boot
 * @package: com.jokey.bingo.delay
 * @comment:
 */
@Order(0)
@Component
public class DelayQueueRunner implements ApplicationRunner {

    @Autowired
    private DelayTaskHandler delayTaskHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        delayTaskHandler.handleDelayMessage();
    }
}
