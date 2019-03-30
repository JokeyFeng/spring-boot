package com.jokey.bingo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JokeyFeng
 * date:2019/3/11
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:
 */
@Data
public class Seckill implements Serializable {

    private static final long serialVersionUID = -6114351612926057790L;

    private long seckillId;

    private String name;

    private Integer number;

    private Date startTime;

    private Date endTime;

    private Date createTime;
}
