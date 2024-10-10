package com.soon83.member;

import com.soon83.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
	private final Long memberId;
	private final String loginId;
	private final String password;
	private final String name;
	private final String email;
	private final UserRole role;

	public String getRoleTitle() {
		return role.getTitle();
	}
}
