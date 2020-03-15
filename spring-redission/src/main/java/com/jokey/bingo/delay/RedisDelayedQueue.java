package com.jokey.bingo.delay;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Zhengjingfeng
 * @created 2020/2/19 21:42
 * @comment Redis延时队列设计说明：
 * 队列具备消息持久化功能，一旦redis开启持久化。
 * 消费者下线后，到期的消息仍然保存在队列里，不会丢弃。
 * 但是，消费者上线后，已到期的消息不会立即投递给消费者，
 * 直到有最新的到期消息要投递了 才会 把之前已到期的消息投递给消费者（这个特点和MQ延时队列不同，使用时请注意）
 */
@Component
public class RedisDelayedQueue {

    private final static Logger LOGGER = LoggerFactory.getLogger(RedisDelayedQueue.class);

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 任务回调监听
     *
     * @param <T>
     */
    public abstract static class BaseDelayTaskEventListener<T> {
        /**
         * 执行方法
         *
         * @param t
         */
        public abstract void invoke(T t);
    }

    /**
     * 添加队列
     *
     * @param t        DTO传输类
     * @param delay    时间数量
     * @param timeUnit 时间单位
     * @param <T>      泛型
     */
    public <T> void addQueue(T t, long delay, TimeUnit timeUnit) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(t.getClass().getName());
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offer(t, delay, timeUnit);
        LOGGER.info("发送延时消息<<=====>>队列：{}，延时：{} 秒，消息内容：{}", t.getClass().getName(), delay, t.toString());
        //delayedQueue.destroy();
    }

    /**
     * 获取队列
     *
     * @param zClass            DTO泛型
     * @param taskEventListener 任务回调监听
     * @param <T>               泛型
     * @return
     */
    public <T> void getQueue(Class zClass, BaseDelayTaskEventListener taskEventListener) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(zClass.getName());
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        new Thread(() -> {
            LOGGER.warn("开启监听延时队列[{}]的消息......", zClass.getName());
            while (true) {
                try {
                    //当队列为空时挂起当前线程，不会消耗CPU
                    T t = blockingFairQueue.take();
                    taskEventListener.invoke(t);
                } catch (InterruptedException e) {
                    LOGGER.error("监听消息出错：{}", e.getMessage());
                }
            }
        }).start();
    }
}