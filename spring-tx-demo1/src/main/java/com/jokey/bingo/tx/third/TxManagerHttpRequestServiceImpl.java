package com.jokey.bingo.tx.third;

import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
import com.lorne.core.framework.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lorne on 2017/11/18
 */
@Slf4j
@Service
public class TxManagerHttpRequestServiceImpl implements TxManagerHttpRequestService {

    @Override
    public String httpGet(String url) {
        log.info("httpGet-start >>>{}", url);
        String res = HttpUtils.get(url);
        log.info("httpGet-end >>>{}", url);
        return res;
    }

    @Override
    public String httpPost(String url, String params) {
        log.info("httpPost-start >>>{},参数:{}", url, params);
        String res = HttpUtils.post(url, params);
        log.info("httpPost-end >>>{},参数:{}", url, params);
        return res;
    }
}
