package com.codingapi.tx;

import com.codingapi.ribbon.loadbalancer.LcnRibbonConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lorne on 2017/6/26.
 */

@Configuration
@ComponentScan
@RibbonClients(defaultConfiguration=LcnRibbonConfiguration.class)
public class TransactionConfiguration {



}
