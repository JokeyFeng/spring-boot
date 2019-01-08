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

package com.jokey.bingo.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.config
 * @ClassName ZookeeperCenterConfig
 * @Author JokeyZheng
 * @Date 2019/1/8
 * @Version 1.0
 */
@Configuration
public class ZookeeperCenterConfig {
    /**
     * 配置zookeeper
     *
     * @param serverList
     * @param namespace  @Value("${zookeeper.center.namespace}") final String namespace
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
            @Value("${regCenter.serverList}") final String serverList, @Value("${regCenter.namespace}") final String namespace
    ) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }
}
