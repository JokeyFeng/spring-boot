/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2019/1/7</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.bingo.task;

import com.jokey.base.annotation.CronTag;
import lombok.extern.slf4j.Slf4j;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.task
 * @ClassName TestTask
 * @Author JokeyZheng
 * @Date 2019/1/7
 * @Version 1.0
 */
@Slf4j
@CronTag(value = "testTask")
public class TestTask {

    public void test0() {
        log.info("无参数测试方法跑起来......");
    }

    public void test1(String params) {
        log.info("有参数:{} 方法跑起来.......", params);
    }
}
