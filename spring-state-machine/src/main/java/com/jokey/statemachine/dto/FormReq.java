package com.jokey.statemachine.dto;

import lombok.Data;

/**
 * @author JokeyFeng
 * @date: 2020/9/12
 * @project: spring-boot
 * @package: com.jokey.statemachine.dto
 * @comment:
 */
@Data
public class FormReq {
	
	private Integer id;
	
	private String title;
	
	private String content;
}
