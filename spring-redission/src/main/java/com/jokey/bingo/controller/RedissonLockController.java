package com.jokey.bingo.controller;

import com.jokey.bingo.core.RedisLockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author JokeyFeng
 * @date: 2019/12/8
 * @project: spring-boot
 * @package: com.jokey.bingo.controller
 * @comment:
 */
@RestController
@RequestMapping("/redisson")
public class RedissonLockController {

    private static Logger log = LoggerFactory.getLogger(RedissonLockController.class);
    /**
     * 锁测试共享变量
     */
    private Integer lockCount = 10;

    /**
     * 无锁测试共享变量
     */
    private Integer count = 10;

    /**
     * 模拟线程数
     */
    private static int threadNum = 10;

    /**
     * 模拟并发测试加锁和不加锁
     *
     * @return
     */
    @GetMapping("/test")
    public void lock() {
        // 计数器
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < threadNum; i++) {
            MyRunnable myRunnable = new MyRunnable(countDownLatch);
            Thread myThread = new Thread(myRunnable);
            myThread.start();
        }
        // 释放所有线程
        countDownLatch.countDown();
    }

    /**
     * 加锁测试
     */
    private void testLockCount() {
        String lockKey = "lock-test";
        try {
            // 加锁，设置超时时间2s
            RedisLockUtil.lock(lockKey, 1, TimeUnit.MINUTES);
            lockCount--;
            log.info("lockCount值：{}", lockCount);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // 释放锁
            RedisLockUtil.unlock(lockKey);
        }
    }

    /**
     * 无锁测试
     */
    private void testCount() {
        count--;
        log.info("count值：{}", count);
    }


    public class MyRunnable implements Runnable {
        /**
         * 计数器
         */
        final CountDownLatch countDownLatch;

        private MyRunnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                // 阻塞当前线程，直到计时器的值为0
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            // 无锁操作
            testCount();
            // 加锁操作
            testLockCount();
        }
    }
}
