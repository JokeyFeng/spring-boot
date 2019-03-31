package com.jokey.bingo.entity;

/**
 * @author JokeyFeng
 * date:2019/3/31
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:
 */
public class ResponseData<T> {
    private int code = 200;
    private String message = "";
    private T data;

    public static ResponseData ok(Object data) {
        return new ResponseData(data);
    }

    public static ResponseData fail() {
        return new ResponseData(null);
    }

    public static ResponseData fail(String message) {
        return new ResponseData(message);
    }

    public static ResponseData fail(String message, int code) {
        return new ResponseData(message, code);
    }

    public static ResponseData failByParam(String message) {
        return new ResponseData(message, ResponseCode.PARAM_ERROR_CODE.getCode());
    }

    public ResponseData(T data) {
        super();
        this.data = data;
    }

    public ResponseData(String message, int code) {
        super();
        this.message = message;
        this.code = code;
    }

    public ResponseData() {
        super();
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
