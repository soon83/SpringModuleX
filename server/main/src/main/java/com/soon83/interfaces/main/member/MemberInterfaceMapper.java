package com.soon83.interfaces.main.member;

import com.soon83.dtos.member.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberInterfaceMapper {

    public MemberSearchCondition toMemberSearchCondition(MemberSearchRequest request) {
        return new MemberSearchCondition(
                request.getMemberId(),
                request.getLoginId(),
                request.getPassword(),
                request.getName(),
                request.getEmail(),
                request.getRole()
        );
    }

    public MemberInfoResponse toMemberInfoResponse(MemberInfo memberInfo) {
        return new MemberInfoResponse(
                memberInfo.getMemberId(),
                memberInfo.getLoginId(),
                memberInfo.getPassword(),
                memberInfo.getName(),
                memberInfo.getEmail(),
                memberInfo.getRole()
        );
    }

    public MemberCreateCommand toMemberCreateCommand(MemberRegisterRequest request) {
        return new MemberCreateCommand(
                request.getLoginId(),
                request.getPassword(),
                request.getName(),
                request.getEmail(),
                request.getRole()
        );
    }

    public MemberBulkCreateCommand toMemberBulkCreateCommand(MemberBulkRegisterRequest request) {
        List<MemberCreateCommand> commandList = request.getRequestList().stream()
                .map(this::toMemberCreateCommand)
                .toList();
        return new MemberBulkCreateCommand(commandList);
    }

    public MemberUpdateCommand toMemberUpdateCommand(MemberEditRequest request) {
        return new MemberUpdateCommand(
                request.getMemberId(),
                request.getLoginId(),
                request.getPassword(),
                request.getName(),
                request.getEmail(),
                request.getRole()
        );
    }

    public MemberBulkUpdateCommand toMemberBulkUpdateCommand(MemberBulkEditRequest request) {
        List<MemberUpdateCommand> commandList = request.getRequestList().stream()
                .map(this::toMemberUpdateCommand)
                .toList();
        return new MemberBulkUpdateCommand(commandList);
    }

    public MemberDeleteCommand toMemberDeleteCommand(Long memberId) {
        return new MemberDeleteCommand(memberId);
    }

    public MemberBulkDeleteCommand toMemberBulkDeleteCommand(MemberBulkRemoveRequest request) {
        List<MemberDeleteCommand> commandList = request.getRequestList().stream()
                .map(MemberRemoveRequest::getMemberId)
                .map(this::toMemberDeleteCommand)
                .toList();
        return new MemberBulkDeleteCommand(commandList);
    }
}
