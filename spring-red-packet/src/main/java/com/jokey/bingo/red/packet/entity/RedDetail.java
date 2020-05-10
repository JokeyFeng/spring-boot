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
 * @comment: 红包金额明细实体
 */
@Data
public class RedDetail implements Serializable {
	
	private static final long serialVersionUID = -9129505307907625695L;
	
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 红包记录ID
	 */
	private Integer recordId;
	/**
	 * 红包随机金额
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
