package com.soon83.interfaces.member;

import com.soon83.dtos.member.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberInterfaceMapper {
    MemberSearchCondition toMemberSearchCondition(MemberSearchRequest request);

    MemberInfoResponse toMemberInfoResponse(MemberInfo info);

    MemberCreateCommand toMemberCreateCommand(MemberRegisterRequest request);

    MemberUpdateCommand toMemberUpdateCommand(MemberEditRequest request);

    MemberDeleteCommand toMemberDeleteCommand(Long memberId);

    @Mapping(source = "requestList", target = "commandList")
    MemberBulkCreateCommand toMemberBulkCreateCommand(MemberBulkRegisterRequest request);

    @Mapping(source = "requestList", target = "commandList")
    MemberBulkUpdateCommand toMemberBulkUpdateCommand(MemberBulkEditRequest request);

    @Mapping(source = "requestList", target = "commandList")
    MemberBulkDeleteCommand toMemberBulkDeleteCommand(MemberBulkRemoveRequest request);
}
