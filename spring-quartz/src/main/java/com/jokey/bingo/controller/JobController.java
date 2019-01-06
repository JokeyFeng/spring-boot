package com.jokey.bingo.controller;

import com.jokey.base.annotation.Log;
import com.jokey.base.entity.MyResponse;
import com.jokey.bingo.entity.Job;
import com.jokey.bingo.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.controller
 * comment:
 */
@Slf4j
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    /**
     * 校验cron表达式是否合法
     *
     * @param cronExpression
     * @return
     */
    @RequestMapping("/checkCron")
    public boolean isLegalForCronExpression(@RequestParam String cronExpression) {
        try {
            return CronExpression.isValidExpression(cronExpression);
        } catch (Exception e) {
            return false;
        }
    }

    @Log("新增定时任务")
    @RequestMapping("/add")
    public MyResponse addJob(Job job) {
        jobService.save(job);
        return MyResponse.ok("新增任务成功！");
    }
}
