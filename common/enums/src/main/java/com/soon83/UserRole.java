package com.soon83;

import com.soon83.mapper.EnumMapperType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole implements EnumMapperType {
	ADMIN("관리자"),
	MANAGER("운영자"),
	MEMBER("회원");

	private final String title;

	@Override
	public String getCode() {
		return this.name();
	}
}