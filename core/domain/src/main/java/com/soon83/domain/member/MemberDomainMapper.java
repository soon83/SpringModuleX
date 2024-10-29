package com.soon83.domain.member;

import com.soon83.dtos.member.MemberInfo;
import com.soon83.entities.member.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberDomainMapper {
    MemberInfo toMemberInfo(Member member);
}
