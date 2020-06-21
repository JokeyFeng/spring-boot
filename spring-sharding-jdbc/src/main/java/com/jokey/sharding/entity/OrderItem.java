package com.jokey.sharding.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author JokeyFeng
 * @date: 2020/6/21
 * @project: spring-boot
 * @package: com.jokey.sharding.entity
 * @comment:
 */
@Data
public class OrderItem implements Serializable {
	
	private static final long serialVersionUID = 3735686032384284949L;
	
	private Long itemId;
	
	private Long orderId;
	
	private String remark;
	
	private LocalDateTime createTime;
	
	private LocalDateTime lastModifyTime;
}

