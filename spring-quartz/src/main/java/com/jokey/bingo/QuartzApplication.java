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

package com.jokey.bingo;

import com.jokey.base.properties.BingoProperties;
import com.jokey.base.util.SpringContextUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo
 * @ClassName QuartzApplication
 * @Author JokeyZheng
 * @Date 2019/1/2
 * @Version 1.0
 */
@MapperScan("com.jokey.*.mapper")
@EnableConfigurationProperties({BingoProperties.class})
@EnableCaching
@EnableAsync
@SpringBootApplication
@Import({SpringContextUtils.class})
public class QuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }
}
