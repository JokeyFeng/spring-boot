package com.jokey.bingo.service;

import com.jokey.base.service.BaseService;
import com.jokey.bingo.entity.Job;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.service
 * comment:
 */
public interface JobService extends BaseService<Job> {
    /**
     * 获取任务
     *
     * @param jobId
     * @return
     */
    Job findJob(Long jobId);

    /**
     * 获取所有任务
     *
     * @return
     */
    List<Job> findAllJobs();

    /**
     * 查找指定的定时任务的所有任务
     *
     * @param job
     * @return
     */
    List<Job> findAllJobs(Job job);

    /**
     * 添加一个定时任务
     *
     * @param job
     * @return
     */
    int addJob(Job job);

    /**
     * 修改一个定时任务
     *
     * @param job
     * @return
     */
    int updateJob(Job job);

    /**
     * 批量更新任务
     *
     * @param jobIds
     * @param status
     * @return
     */
    int updateBatch(String jobIds, String status);

    /**
     * 批量删除任务
     *
     * @param jobIds
     * @return
     */
    int deleteBatch(String jobIds);

    /**
     * 立即运行定时任务
     * @param jobIds
     */
    void run(String jobIds);

    /**
     * 暂停定时任务
     *
     * @param jobIds
     */
    void pauseJob(String jobIds);

    /**
     * 恢复定时任务
     *
     * @param jobIds
     */
    void resumeJob(String jobIds);

    /**
     * 查询
     *
     * @return
     */
    @Cacheable(key = "#p0")
    List<Job> getSysCronClazz();

}
