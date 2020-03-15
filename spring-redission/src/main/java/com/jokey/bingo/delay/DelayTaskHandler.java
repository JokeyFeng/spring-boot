package com.jokey.bingo.delay;

import com.jokey.bingo.dto.DelayedTaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * @author Zhengjingfeng
 * @created 2020/2/19 23:15
 * @comment 具体业务实现类
 */
@Component
public class DelayTaskHandler extends AbstractDelayedTaskHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(DelayTaskHandler.class);

    @Autowired
    private RedisDelayedQueue redisDelayedQueue;

    @Resource
    @Qualifier("delayAsyncExecutor")
    private Executor threadPoolExecutor;

    @Override
    public void handleDelayMessage() {
        //监听延迟队列
        RedisDelayedQueue.BaseDelayTaskEventListener<DelayedTaskDTO> taskEventListener =
                new RedisDelayedQueue.BaseDelayTaskEventListener<DelayedTaskDTO>() {
                    @Override
                    public void invoke(DelayedTaskDTO taskBodyDTO) {
                        threadPoolExecutor.execute(new Task(taskBodyDTO));
                    }
                };
        threadPoolExecutor.execute(() -> redisDelayedQueue.getQueue(DelayedTaskDTO.class, taskEventListener));
    }

    private class Task implements Runnable {

        private DelayedTaskDTO delayTask;

        private Task(DelayedTaskDTO delayTask) {
            this.delayTask = delayTask;
        }

        @Override
        public void run() {
            if (Objects.nonNull(delayTask)) {
                LOGGER.info(delayTask.toString());
            }
        }
    }
}
