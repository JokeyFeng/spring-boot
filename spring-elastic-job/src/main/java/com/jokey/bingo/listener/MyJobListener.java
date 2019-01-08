/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019/1/8</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.bingo.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.listener
 * @ClassName MyJobListener
 * @Author JokeyZheng
 * @Date 2019/1/8
 * @Version 1.0
 */
@Slf4j
public class MyJobListener implements ElasticJobListener {
    private long beginTime = 0;

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();

        log.info("===>{} JOB BEGIN TIME: {} <===", shardingContexts.getJobName(), new Date());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        log.info("===>{} JOB END TIME: {},TOTAL CAST: {} <===", shardingContexts.getJobName(), new Date(), endTime - beginTime);
    }
}
