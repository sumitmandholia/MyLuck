package com.authenticationService.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.authenticationService.model.AuditorEntity;

@Entity
@Table(name="ROLES")
public class Role extends AuditorEntity implements GrantedAuthority {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5491769523784372915L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLEID_GEN")
	@SequenceGenerator(name = "ROLEID_GEN", sequenceName = "ROLEID_SEQ",allocationSize = 1,initialValue = 1)
	@Column(name="ROLE_ID")
	private Long roleId;
	
	@Column(name="ROLE_NAME", nullable = false, unique = true)
	private String roleName;
	
	@Column(name="DESCRIPTION")
	private String roleDesciption;

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

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return roleName;
	}	
	
}
