package com.soon83.member;

import org.hibernate.annotations.Comment;

import com.soon83.BaseEntity;
import com.soon83.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member",
	indexes = {
		@Index(name = "ix_loginId", columnList = "login_id"),
		@Index(name = "ix_name", columnList = "name"),
	},
	uniqueConstraints = {
		@UniqueConstraint(name = "uix_loginId", columnNames = {"login_id"})}
)
@Comment("회원")
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id", nullable = false)
	@Comment("아이디")
	private Long memberId;

	@Column(name = "login_id", nullable = false, length = 63)
	@Comment("로그인 아이디")
	private String loginId;

	@Column(name = "password", nullable = false)
	@Comment("비밀번호")
	private String password;

	@Column(name = "name", nullable = false, length = 31)
	@Comment("이름")
	private String name;

	@Column(name = "email", nullable = false, length = 31)
	@Comment("이메일")
	private String email;

	@Column(name = "role", nullable = false, length = 31)
	@Enumerated(EnumType.STRING)
	@Comment("역할")
	private UserRole role;
}