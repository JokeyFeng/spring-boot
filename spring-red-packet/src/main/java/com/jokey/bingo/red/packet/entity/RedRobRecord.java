package com.jokey.bingo.red.packet.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author JokeyFeng
 * @date: 2020/5/9
 * @project: spring-boot
 * @package: com.jokey.bingo.red.packet.entity
 * @comment: 抢到红包时金额等相关信息记录表
 */
@Data
public class RedRobRecord implements Serializable {
	
	private static final long serialVersionUID = 3973636152952955434L;
	
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 红包全局唯一标识串
	 */
	private String redPacket;
	/**
	 * 红包总金额
	 */
	private BigDecimal amount;
	/**
	 * 是否有效
	 */
	private Byte isActive;
	/**
	 * 抢到时间
	 */
	private LocalDateTime robTime;
}
