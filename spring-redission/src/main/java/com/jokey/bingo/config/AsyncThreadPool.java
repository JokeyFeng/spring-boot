package com.jokey.bingo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Zhengjingfeng
 * @created 2019/9/11 10:46
 * @comment 自定义线程池
 */
@EnableAsync
@Configuration
public class AsyncThreadPool implements AsyncConfigurer {

    private final static Logger LOGGER = LoggerFactory.getLogger(AsyncThreadPool.class);

    @Bean(name = "delayAsyncExecutor")
    public Executor getDelayAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //设置核心线程数，默认为1
        threadPool.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        threadPool.setMaxPoolSize(20);
        // 当核心线程都在跑任务，还有多余的任务会存到此处。
        threadPool.setQueueCapacity(30);
        // 设置线程活跃时间（秒）
        threadPool.setKeepAliveSeconds(120);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 15);
        threadPool.setThreadNamePrefix("DelayTask-");
        //自定义任务拒绝策略 - 让任务重新入队列
        threadPool.setRejectedExecutionHandler(new ReenterRejectedExecutionHandler());
        threadPool.initialize();
        return threadPool;
    }

    /**
     * 自定义任务拒绝策略 - 让任务重新入队列
     */
    class ReenterRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                // 核心改造点，由blockingQueue的offer改成put阻塞方法，具体查看offer与put方法的区别
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                LOGGER.error("the queue is full，error happened when a task is reset into the queue：{}", e.getMessage());
            }
        }
    }
}