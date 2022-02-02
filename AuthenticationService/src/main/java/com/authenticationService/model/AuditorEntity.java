package com.authenticationService.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditorEntity {
	
	@Column(name="CREATED_BY")
	@CreatedBy
	public String createdBy;
	
	@Column(name="CREATED_AT")
	@CreationTimestamp
	public Timestamp createdAt;
	
	@Column(name="LAST_MODIFIED_BY")
	@LastModifiedBy
	public String lastModifiedBy;
	
	@Column(name="LAST_MODIFIED_AT")
	@UpdateTimestamp
	public Timestamp lastModifiedAt;

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
