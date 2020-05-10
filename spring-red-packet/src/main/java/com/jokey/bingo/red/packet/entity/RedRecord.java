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
 * @comment: 发红包记录实体
 */
@Data
public class RedRecord implements Serializable {
	
	private static final long serialVersionUID = 137284045736805992L;
	
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
	 * 红包指定可以抢的总人数，即红包个数
	 */
	private Integer total;
	/**
	 * 红包总金额
	 */
	private BigDecimal amount;
	/**
	 * 是否有效
	 */
	private Byte isActive;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
}
