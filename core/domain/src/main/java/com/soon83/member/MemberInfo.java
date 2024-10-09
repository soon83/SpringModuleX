package com.soon83.member;

import com.soon83.UserRole;

import lombok.Getter;

@Getter
public class MemberInfo {
	private final Long userId;
	private final String loginId;
	private final String password;
	private final String name;
	private final String email;
	private final UserRole role;

	public MemberInfo(Member member) {
		this.userId = member.getUserId();
		this.loginId = member.getLoginId();
		this.password = member.getPassword();
		this.name = member.getName();
		this.email = member.getEmail();
		this.role = member.getRole();
	}
}
