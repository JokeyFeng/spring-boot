package com.jokey.bingo.red.packet.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author JokeyFeng
 * @date: 2020/5/10
 * @project: spring-boot
 * @package: com.jokey.bingo.red.packet.dto
 * @comment:
 */
@Data
public class RedPacketDTO implements Serializable {
	
	private static final long serialVersionUID = -1180575896665871570L;
	
	/**
	 * 用户账号ID
	 */
	@NotNull(message = "用户账号ID必传")
	private Integer userId;
	/**
	 * 红包个数
	 */
	@NotNull(message = "红包个数必传")
	private Integer total;
	/**
	 * 红包总金额(单位：分)
	 */
	@NotNull(message = "红包总金额必传")
	private Integer amount;
}
