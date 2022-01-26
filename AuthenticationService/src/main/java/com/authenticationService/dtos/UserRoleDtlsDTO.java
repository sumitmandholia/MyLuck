package com.authenticationService.dtos;

import java.util.HashSet;
import java.util.Set;

import com.authenticationService.entities.Role;
import com.authenticationService.util.UserStatus;

public class UserRoleDtlsDTO {

	private Long userId;
	private String loginId;
	private String password;
	private String emailId;
	private Long phoneNumber;
	private UserStatus userStatus;
	private Boolean isPasswordExpired;
	private Set<Role> roles = new HashSet<Role>(0);
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public UserStatus getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	public Boolean getIsPasswordExpired() {
		return isPasswordExpired;
	}
	public void setIsPasswordExpired(Boolean isPasswordExpired) {
		this.isPasswordExpired = isPasswordExpired;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRoleDtlsDTO [userId=");
		builder.append(userId);
		builder.append(", loginId=");
		builder.append(loginId);
		builder.append(", password=");
		builder.append(password);
		builder.append(", emailId=");
		builder.append(emailId);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", userStatus=");
		builder.append(userStatus);
		builder.append(", isPasswordExpired=");
		builder.append(isPasswordExpired);
		builder.append(", roles=");
		builder.append(roles);
		builder.append("]");
		return builder.toString();
	}
	
	
}
