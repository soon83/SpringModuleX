package com.soon83.member;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	MemberInfo toMemberInfo(Member member);

	Member toMember(MemberCreateCommand memberCreateCommand);

	Member toMember(MemberUpdateCommand memberUpdateCommand);
}