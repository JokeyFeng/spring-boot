package com.codingapi.tx.netty.service;

/**
 * @author lorne on 2017/11/17
 */
public interface TxManagerHttpRequestService {
    /**
     * get请求
     *
     * @param url
     * @return
     */
    String httpGet(String url);

    /**
     * post请求
     *
     * @param url
     * @param params
     * @return
     */
    String httpPost(String url, String params);

}
