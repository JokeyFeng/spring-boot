package com.jokey.bingo.dto;

import lombok.Data;

/**
 * @author JokeyFeng
 * date:2019/3/18
 * project:spring-boot
 * package:com.jokey.bingo.dto
 * comment:
 */

@Data
public class PanicBuyResult<T> {

    private Boolean success;

    private T data;


    private String error;

    public PanicBuyResult(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public PanicBuyResult(Boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}
