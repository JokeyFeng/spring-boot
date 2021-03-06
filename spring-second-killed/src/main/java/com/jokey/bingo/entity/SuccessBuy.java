package com.jokey.bingo.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author JokeyFeng
 * date:2019/3/11
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:
 */
@Data
public class SuccessBuy implements Serializable {

    private static final long serialVersionUID = -7444520230047575306L;

    private long buyId;

    private long userPhone;

    private short state;

    private Date createTime;

    private PanicBuyEntity panicBuyEntity;
}
