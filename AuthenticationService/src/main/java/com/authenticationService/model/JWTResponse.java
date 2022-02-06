package com.authenticationService.model;

import java.io.Serializable;

public class JWTResponse implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5874403138704400744L;
	
	private String token;
	private String username;
	private Long userId;
	
	public JWTResponse(String token) {
		super();
		this.token = token;
	}

	public JWTResponse(String token, String username) {
		// TODO Auto-generated constructor stub
		this.token = token;
		this.username = username;
	}

	public JWTResponse(String token, String username, Long userId) {
		// TODO Auto-generated constructor stub
		this.token = token;
		this.username = username;
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
