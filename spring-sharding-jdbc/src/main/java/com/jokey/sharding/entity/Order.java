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
public class Order implements Serializable {
	
	private static final long serialVersionUID = 8567671301638105170L;
	
	private Long orderId;
	
	private Long userId;
	
	private Integer configId;
	
	private String remark;
	
	private LocalDateTime createTime;
	
	private LocalDateTime lastModifyTime;
}
