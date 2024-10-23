package com.soon83.member;

import com.soon83.tables.records.MemberRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberDomainMapper {
	MemberInfo toMemberInfo(Member member);

	MemberInfo toMemberInfo(MemberRecord member);

	Member toMemberEntity(MemberCreateCommand memberCreateCommand);

	Member toMemberEntity(MemberUpdateCommand memberUpdateCommand);
}