package com.jokey.bingo.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.entity
 * comment:
 */
@Data
@Table(name = "t_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = -998141803413213471L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "ID")
    private Long id;

    /**
     * 操作用户
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * 描述
     */
    @Column(name = "OPERATION")
    private String operation;

    /**
     * 耗时(毫秒)
     */
    @Column(name = "TIME")
    private Long time;

    /**
     * 操作方法
     */
    @Column(name = "METHOD")
    private String method;

    /**
     * 参数
     */
    @Column(name = "PARAMS")
    private String params;

    /**
     * IP地址
     */
    @Column(name = "IP")
    private String ip;

    /**
     * 操作时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 地点
     */
    @Column(name = "LOCATION")
    private String location;

    /**
     * 用于搜索条件中的时间字段
     */
    @Transient
    private String timeField;
}
