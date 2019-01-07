package com.jokey.bingo.controller;

import com.alibaba.fastjson.JSON;
import com.jokey.base.annotation.Log;
import com.jokey.base.entity.MyResponse;
import com.jokey.bingo.entity.Job;
import com.jokey.bingo.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/checkCron")
    public boolean isLegalForCronExpression(@RequestParam String cronExpression) {
        try {
            return CronExpression.isValidExpression(cronExpression);
        } catch (Exception e) {
            return false;
        }
    }

    @Log("新增定时任务")
    @PostMapping("/add")
    public MyResponse addJob(@RequestBody Job job) {
        jobService.addJob(job);
        return MyResponse.ok("新增任务成功！");
    }


    @Log("立即执行定时任务")
    @PutMapping("/run")
    public MyResponse runJob(@RequestParam String jobIds) {
        try {
            jobService.run(jobIds);
            return MyResponse.ok();
        } catch (Exception e) {
            return MyResponse.error();
        }
    }

    @Log("暂停定时任务")
    @PutMapping("/pause")
    public MyResponse pauseJob(@RequestParam String jobIds) {
        try {
            jobService.pauseJob(jobIds);
            return MyResponse.ok();
        } catch (Exception e) {
            return MyResponse.error();
        }
    }

    /**
     * @return MyResponse
     */
    @GetMapping("/getSysCronClazz")
    public MyResponse getSysCronClazz() {
        List<Job> sysCronClazz = this.jobService.getSysCronClazz();
        return MyResponse.ok(sysCronClazz);
    }

    public static void main(String[] args) {
        Job job = new Job();
        job.setBeanName("testTask");
        job.setMethodName("test0");
        job.setCronExpression("0 0-50 11 * * ?");
        job.setStatus("1");
        job.setRemark("无参数测试方法");
        System.out.println(JSON.toJSONString(job));
    }
}
