package com.soon83.entities.member;

import com.soon83.dtos.enums.UserRole;
import com.soon83.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member",
	indexes = {
		@Index(name = "ix_loginId", columnList = "login_id"),
		@Index(name = "ix_name", columnList = "name"),
	},
	uniqueConstraints = {
		@UniqueConstraint(name = "uix_loginId", columnNames = {"login_id"})}
)
@Entity
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