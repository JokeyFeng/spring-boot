package com.jokey.bingo.service;

import com.jokey.base.service.BaseService;
import com.jokey.bingo.entity.JobLog;

import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.service
 * comment:
 */
public interface JobLogService extends BaseService<JobLog> {

    /**
     * 获取所有定时任务日志
     *
     * @param jobLog
     * @return
     */
    List<JobLog> findAllJobLogs(JobLog jobLog);

    /**
     * 保存定时任务日志
     *
     * @param jobLog
     * @return
     */
    int saveJobLog(JobLog jobLog);

    /**
     * 批量删除定时任务日志
     *
     * @param jobLogIds
     * @return
     */
    int deleteBatch(String jobLogIds);
}
