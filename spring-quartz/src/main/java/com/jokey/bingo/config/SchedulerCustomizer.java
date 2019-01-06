package com.jokey.bingo.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 定时任务实体类配置
 *
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.config
 * comment:
 */
@Configuration
@EnableScheduling
public class SchedulerCustomizer implements SchedulerFactoryBeanCustomizer {
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        // 延时启动
        schedulerFactoryBean.setStartupDelay(2);
        // 设置自动启动，默认为 true
        schedulerFactoryBean.setAutoStartup(true);
        // 启动时更新己存在的 Job，这样就不用每次修改 targetObject后删除 qrtz_job_details表对应记录了
        schedulerFactoryBean.setOverwriteExistingJobs(true);
    }
}
