package com.jokey.bingo.util;

import com.jokey.base.util.SpringContextUtils;
import com.jokey.bingo.entity.Job;
import com.jokey.bingo.entity.JobLog;
import com.jokey.bingo.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.util
 * comment:
 */
@Slf4j
public class QuartzJob extends QuartzJobBean {

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {

        Job job = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);
        //获取spring Bean
        JobLogService jobLogService = (JobLogService) SpringContextUtils.getBean("JobLogService");

        //构建日志对象
        JobLog jobLog = new JobLog();
        jobLog.setJobId(job.getJobId());
        jobLog.setBeanName(job.getBeanName());
        jobLog.setMethodName(job.getMethodName());
        jobLog.setParams(job.getParams());
        jobLog.setCreateTime(new Date());

        long startTime = System.currentTimeMillis();

        //执行任务
        try {
            log.info("准备执行任务，任务ID：{}", job.getJobId());
            QuartzRunnable task = new QuartzRunnable(job.getBeanName(), job.getMethodName(), job.getParams());

            Future<?> future = executor.submit(task);
            future.get();

            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes(times);
            //任务状态 0=成功 1=失败
            jobLog.setStatus("0");

            log.info("执行任务完毕，任务ID：{} 总耗时：{} 毫秒", job.getJobId(), times);
        } catch (Exception e) {
            log.error("执行任务失败，任务ID：{} 异常信息：{}", job.getJobId(), e.getMessage());
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes(times);
            //任务状态 0=成功 1=失败
            jobLog.setStatus("1");
            jobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            jobLogService.saveJobLog(jobLog);
        }
    }
}
