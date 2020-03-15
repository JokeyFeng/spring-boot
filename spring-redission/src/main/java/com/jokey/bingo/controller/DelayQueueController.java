package com.jokey.bingo.controller;

import com.jokey.bingo.delay.RedisDelayedQueue;
import com.jokey.bingo.dto.DelayedTaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author JokeyFeng
 * @date: 2020/3/15
 * @project: spring-boot
 * @package: com.jokey.bingo.controller
 * @comment:
 */
@RestController
@RequestMapping("/delay-queue")
public class DelayQueueController {

    @Autowired
    private RedisDelayedQueue delayedQueue;

    @PostMapping("/send")
    public Map<String, Boolean> sendMessage(@RequestBody DelayedTaskDTO taskDTO) {
        delayedQueue.addQueue(taskDTO, taskDTO.getExpireTime(), TimeUnit.SECONDS);
        Map<String, Boolean> map = new HashMap<>(1);
        map.put("isSuccess", Boolean.TRUE);
        return map;
    }
}
