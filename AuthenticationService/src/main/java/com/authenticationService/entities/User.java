package com.authenticationService.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.authenticationService.util.UserStatus;

@Entity
@Table(name="USERS")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userid_gen")
	@SequenceGenerator(name = "userid_gen", sequenceName = "userid_seq", initialValue = 1000, allocationSize = 1)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "LOGIN_ID", nullable = false, unique = true)
	private String loginId;
	
	@Column(name="LOGIN_PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "EMAIL_ID", unique = true)
	private String emailId;
	
	@Column(name = "PHONE_ID", unique = true)
	private Long phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "USER_STATUS", nullable = false)
	private UserStatus userStatus;
	
	@Column(name = "IS_PASSWORD_EXPIRED", nullable = false)
	@ColumnDefault("false")
	private Boolean isPasswordExpired;
	
	@Column(name="CREATED_BY")
	@CreatedBy
	private String createdBy;
	
	@Column(name="CREATED_AT")
	@CreationTimestamp
	private Timestamp createdAt;
	
	@Column(name="LAST_MODIFIED_BY")
	@LastModifiedBy
	private String lastModifiedBy;
	
	@Column(name="LAST_MODIFIED_AT")
	@UpdateTimestamp
	private Timestamp lastModifiedAt;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Timestamp lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}
