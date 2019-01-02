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

package com.jokey.bingo.service;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.service
 * @ClassName DemoQuartzJob
 * @Author JokeyZheng
 * @Date 2019/1/2
 * @Version 1.0
 */
@Component
public class DemoQuartzJob implements Job {
    /**
     * 实现定时任务具体要处理的逻辑
     *
     * @param
     * @return
     */
    @Override
    public void execute(JobExecutionContext context) {

        // 解析创建该定时任务时设置的数据
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String userName = (String) dataMap.get("userName");
        Map work = (Map) dataMap.get("work");

        // 输出 该用户在这个时刻要做的事情
        System.out.println("" + userName + " is work:" + work);

        // todo 要做的事情的具体实现

    }
}
