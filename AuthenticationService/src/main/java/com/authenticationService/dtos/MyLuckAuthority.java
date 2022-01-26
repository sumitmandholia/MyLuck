package com.authenticationService.dtos;

import org.springframework.security.core.GrantedAuthority;

public class MyLuckAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String role;
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role;
	}

}
