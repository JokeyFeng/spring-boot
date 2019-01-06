/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019/1/2</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.bingo.util;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Quartz常用操作工具类
 *
 * @Project spring-boot
 * @Package com.jokey.bingo.util
 * @ClassName QuartzManager
 * @Author JokeyZheng
 * @Date 2019/1/2
 * @Version 1.0
 */
@Deprecated
@Slf4j
//@Component
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    /**
     * @param name      任务名
     * @param groupName 任务组名
     * @param jobClass  任务
     * @param cron      时间设置，参考quartz说明文档
     * @param paramMap  任务执行所需的参数
     * @Description: 添加一个定时任务
     */
    public void addJob(String name, String groupName, Class jobClass, String cron, Map<String, Object> paramMap) {
        try {
//            Scheduler sched = schedulerFactory.getScheduler()
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(name, groupName).build();

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(name, groupName);
//            triggerBuilder.withPriority(1)
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            // 业务逻辑参数传递
           /* if (paramMap.get(Constant.CHANNEL) != null) {
                if (jobDetail.getJobDataMap().get(Constant.CHANNEL) != null) {
                    log.info("=======channelJson = " + jobDetail.getJobDataMap().get(Constant.CHANNEL));
                }
                Object channel = paramMap.get(Constant.CHANNEL);
                String channelJson = JSONObject.toJSONString(channel);
                jobDetail.getJobDataMap().put(Constant.CHANNEL, channelJson);
            }
            if (paramMap.get(Constant.PROGRAM) != null) {
                Program program = (Program) paramMap.get(Constant.PROGRAM);
                String programJson = JSONObject.toJSONString(program);
                jobDetail.getJobDataMap().put(Constant.PROGRAM, programJson);
            }*/

            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param name
     * @param groupName
     * @param cron      时间设置，参考quartz说明文档
     * @Description: 修改一个任务的触发时间
     */
    public void modifyJobTime(String name, String groupName, String cron) {
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(name, groupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }


            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(name, groupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */


                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param name
     * @param groupName
     * @Description: 移除一个任务
     */
    public void removeJob(String name, String groupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(name, groupName);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(name, groupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description:启动所有定时任务
     */
    public void startJobs() {
        try {

            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description:关闭所有定时任务
     */
    public void shutdownJobs() {
        try {

            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }
}
