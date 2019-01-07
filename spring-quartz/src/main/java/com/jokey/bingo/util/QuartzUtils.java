package com.jokey.bingo.util;

import com.jokey.bingo.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;

/**
 * 定时任务工具类
 *
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.util
 * comment:
 */
@Slf4j
public abstract class QuartzUtils {

    private static final String JOB_NAME = "TASK_";

    /**
     * 获取触发器key
     *
     * @param jobId
     * @return
     */
    private static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    /**
     * 获取jobKey
     *
     * @param jobId
     * @return
     */
    private static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     *
     * @param scheduler
     * @param jobId
     * @return
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            log.error("获取Cron表达式失败-->{}", e);
        }
        return null;
    }

    /**
     * 暂停任务
     *
     * @param scheduler
     * @param jobId
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败-->{}", e);
        }
    }

    /**
     * 创建定时任务
     *
     * @param scheduler
     * @param scheduleJob
     */
    public static void createScheduleJob(Scheduler scheduler, Job scheduleJob) {
        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();

            //表达式调度构建器
            CronScheduleBuilder cronBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();

            //按照新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).withSchedule(cronBuilder).build();

            //放入参数，运行时的方法获取
            jobDetail.getJobDataMap().put(Job.JOB_PARAM_KEY, scheduleJob);
            scheduler.scheduleJob(jobDetail, trigger);

            //暂停任务
            if (StringUtils.equals(scheduleJob.getStatus(), Job.ScheduleStatus.PAUSE.getValue())) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }
        } catch (SchedulerException e) {
            log.error("构建定时任务失败-->{}", e);
        }
    }

    /**
     * 更新定时任务
     *
     * @param scheduler
     * @param scheduleJob
     */
    public static void updateScheduleJob(Scheduler scheduler, Job scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

            //表达式调度构建器
            CronScheduleBuilder cronBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());

            if (trigger == null) {
                throw new SchedulerException("获取Cron表达式失败");
            } else {
                //按照新的cronExpression表达式构建新的trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronBuilder).build();
                //设置参数
                trigger.getJobDataMap().put(Job.JOB_PARAM_KEY, scheduleJob);
            }
            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停任务
            if (StringUtils.equals(scheduleJob.getStatus(), Job.ScheduleStatus.PAUSE.getValue())) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }

        } catch (SchedulerException e) {
            log.error("更新定时任务失败-->{}", e.getMessage());
        }
    }

    /**
     * 立即执行任务
     *
     * @param scheduler
     * @param scheduleJob
     */
    public static void run(Scheduler scheduler, Job scheduleJob) {

        try {
            //设置参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(Job.JOB_PARAM_KEY, scheduleJob);
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
        } catch (SchedulerException e) {
            log.error("立即执行定时任务失败-->{}", e.getMessage());
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param jobId
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("恢复定时任务失败-->{}", e.getMessage());
        }
    }

    /**
     * 删除任务
     *
     * @param jobId
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败-->{}", e.getMessage());
        }
    }
}
