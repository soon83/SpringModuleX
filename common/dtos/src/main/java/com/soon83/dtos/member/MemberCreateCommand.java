package com.soon83.dtos.member;

import com.soon83.dtos.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberCreateCommand {
	private String loginId;
	private String password;
	private String name;
	private String email;
	private UserRole role;
}
