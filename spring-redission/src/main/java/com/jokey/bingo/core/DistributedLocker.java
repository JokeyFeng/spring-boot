package com.jokey.bingo.core;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author JokeyFeng
 * @date: 2019/12/8
 * @project: spring-boot
 * @package: com.jokey.bingo.core
 * @comment:
 */
public interface DistributedLocker {
    /**
     * 加锁
     *
     * @param lockKey
     * @return org.redisson.api.RLock
     * @author JokeyZheng
     * @date 2019/12/8 11:14
     */
    RLock lock(String lockKey);

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param timeout
     * @return org.redisson.api.RLock
     * @author JokeyZheng
     * @date 2019/12/8 11:15
     */
    RLock lock(String lockKey, int timeout);

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param unit
     * @param timeout
     * @return org.redisson.api.RLock
     * @author JokeyZheng
     * @date 2019/12/8 11:16
     */
    RLock lock(String lockKey, TimeUnit unit, int timeout);

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    /**
     * 释放锁
     *
     * @param lockKey
     * @return void
     * @author JokeyZheng
     * @date 2019/12/8 11:17
     */
    void unlock(String lockKey);

    /**
     * 释放锁
     *
     * @param lock
     * @return void
     * @author JokeyZheng
     * @date 2019/12/8 11:17
     */
    void unlock(RLock lock);
}
