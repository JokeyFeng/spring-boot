package com.jokey.base.entity;

import java.util.HashMap;

/**
 * 响应数据结构
 *
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.base.entity
 * comment:
 */
public class MyResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    /**
     * 成功
     */
    private static final Integer SUCCESS = 0;
    /**
     * 警告
     */
    private static final Integer WARN = 1;
    /**
     * 异常 失败
     */
    private static final Integer FAIL = 500;
    /**
     * 未认证
     */
    private static final Integer UNAUTHORIZED = 401;
    /**
     * 超频
     */
    private static final Integer OVERCLOCKING = 666;

    private MyResponse() {
        put("code", SUCCESS);
        put("msg", "操作成功");
    }

    public static MyResponse error(Object msg) {
        MyResponse response = new MyResponse();
        response.put("code", FAIL);
        response.put("msg", msg);
        return response;
    }

    public static MyResponse warn(Object msg) {
        MyResponse response = new MyResponse();
        response.put("code", WARN);
        response.put("msg", msg);
        return response;
    }

    public static MyResponse ok(Object msg) {
        MyResponse response = new MyResponse();
        response.put("code", SUCCESS);
        response.put("msg", msg);
        return response;
    }

    public static MyResponse unAuthorized(Object msg) {
        MyResponse response = new MyResponse();
        response.put("code", UNAUTHORIZED);
        response.put("msg", msg);
        return response;
    }

    public static MyResponse overClocking(Object msg) {
        MyResponse response = new MyResponse();
        response.put("code", OVERCLOCKING);
        response.put("msg", msg);
        return response;
    }

    public static MyResponse ok() {
        return new MyResponse();
    }

    public static MyResponse error() {
        return MyResponse.error("");
    }

    @Override
    public MyResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
