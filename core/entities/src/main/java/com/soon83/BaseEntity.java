package com.soon83;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	@CreationTimestamp
	@Comment("생성일시")
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Comment("수정일시")
	@Column(name = "modified_at", nullable = false)
	private LocalDateTime modifiedAt;

	@CreatedBy
	@Comment("생성인")
	@Column(name = "created_by")
	private Long createdBy;

	@LastModifiedBy
	@Comment("수정인")
	@Column(name = "modified_by")
	private Long modifiedBy;
}