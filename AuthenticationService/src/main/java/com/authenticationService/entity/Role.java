package com.authenticationService.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Entity
@Table(name="ROLES")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLEID_GEN")
	@SequenceGenerator(name = "ROLEID_GEN", sequenceName = "ROLEID_SEQ",allocationSize = 1,initialValue = 1)
	@Column(name="ROLE_ID")
	private Long roleId;
	
	@Column(name="ROLE_NAME", nullable = false, unique = true)
	private String roleName;
	
	@Column(name="DESCRIPTION")
	private String roleDesciption;

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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesciption() {
		return roleDesciption;
	}

	public void setRoleDesciption(String roleDesciption) {
		this.roleDesciption = roleDesciption;
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
	
	
	
}
