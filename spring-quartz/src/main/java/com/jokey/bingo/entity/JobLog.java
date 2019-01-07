package com.jokey.bingo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "t_job_log")
public class JobLog implements Serializable {

    private static final long serialVersionUID = -7114915445674333148L;

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "LOG_ID")
    private Long logId;

    /**
     * 任务ID
     */
    @Column(name = "JOB_ID")
    private Long jobId;

    /**
     * Bean名称
     */
    @Column(name = "BEAN_NAME")
    private String beanName;

    /**
     * 方法名称
     */
    @Column(name = "METHOD_NAME")
    private String methodName;

    /**
     * 参数
     */
    @Column(name = "PARAMS")
    private String params;

    /**
     * 状态。0=成功，1=失败
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 异常信息
     */
    @Column(name = "ERROR")
    private String error;

    /**
     * 耗时(毫秒)
     */
    @Column(name = "TIMES")
    private Long times;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
}
