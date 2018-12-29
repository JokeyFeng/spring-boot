package com.jokey.bingo.tx.third;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author lorne on 2017/11/18
 */
@Slf4j
@Service
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {

    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${tx.manager.serviceName:tx-manager}")
    private String serviceName;

    @Override
    public String getTxUrl() {
        Random random = new Random();
        List<ServiceInstance> instanceList = discoveryClient.getInstances(serviceName);
        int index = random.nextInt(instanceList.size());
        ServiceInstance serviceInstance = instanceList.get(index);
        String url = String.format("%s/tx/manager/", serviceInstance.getUri());
        log.info("load tm.manager.url >>>{}", url);
        return url;
    }
}