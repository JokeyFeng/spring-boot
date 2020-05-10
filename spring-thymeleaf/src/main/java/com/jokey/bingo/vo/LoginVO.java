package com.jokey.bingo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JokeyFeng
 * @date: 2020/4/23
 * @project: spring-boot
 * @package: com.jokey.bingo.vo
 * @comment:
 */
@Data
public class LoginVO implements Serializable {
	
	private static final long serialVersionUID = -5379937558174527705L;
	
	private String loginName;
	
	private String password;
}
