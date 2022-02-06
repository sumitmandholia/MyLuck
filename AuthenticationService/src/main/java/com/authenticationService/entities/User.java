package com.authenticationService.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.authenticationService.model.AuditorEntity;
import com.authenticationService.util.UserStatus;

@Entity
@Table(name="USERS")
public class User extends AuditorEntity implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7613977158752489481L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userid_gen")
	@SequenceGenerator(name = "userid_gen", sequenceName = "userid_seq", initialValue = 1000, allocationSize = 1)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "LOGIN_ID", nullable = false, unique = true)
	private String username;
	
	@Column(name="LOGIN_PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "EMAIL_ID", unique = true)
	private String emailId;
	
	@Column(name = "PHONE_ID", unique = true)
	private Long phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "USER_STATUS", nullable = false)
	private UserStatus userStatus;
	
	@Column(name = "IS_PASSWORD_EXPIRED", columnDefinition="BOOLEAN DEFAULT false", nullable = false)
	private boolean isPasswordExpired; 
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles = new HashSet<Role>(0);

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public boolean getIsPasswordExpired() {
		return isPasswordExpired;
	}

	public void setIsPasswordExpired(boolean isPasswordExpired) {
		this.isPasswordExpired = isPasswordExpired;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return !(UserStatus.EXPIRED.equals(this.userStatus));
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !(UserStatus.LOCKED.equals(this.userStatus));
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return !isPasswordExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return UserStatus.ACTIVE.equals(this.userStatus);
	}
	
	

}
