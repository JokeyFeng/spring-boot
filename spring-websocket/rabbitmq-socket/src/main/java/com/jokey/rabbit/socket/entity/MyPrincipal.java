package com.jokey.rabbit.socket.entity;

import java.security.Principal;

/**
 * @author jokey
 */
public class MyPrincipal implements Principal {
	
	private String loginName;
	
	public MyPrincipal(String loginName) {
		this.loginName = loginName;
	}
	
	@Override
	public String getName() {
		return loginName;
	}
}
