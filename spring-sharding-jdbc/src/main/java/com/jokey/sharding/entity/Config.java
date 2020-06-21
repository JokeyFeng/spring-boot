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
public class Config implements Serializable {
	
	private static final long serialVersionUID = 7729515753620214565L;
	
	private Integer id;
	
	private String remark;
	
	private LocalDateTime createTime;
	
	private LocalDateTime lastModifyTime;
}
