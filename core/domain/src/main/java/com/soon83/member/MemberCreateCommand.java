package com.soon83.member;

import com.soon83.enums.UserRole;

import lombok.Getter;

@Getter
public class MemberCreateCommand {
	private String loginId;
	private String password;
	private String name;
	private String email;
	private UserRole role;
}
