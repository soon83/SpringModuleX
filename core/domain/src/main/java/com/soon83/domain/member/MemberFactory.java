package com.soon83.domain.member;

import com.soon83.dtos.member.MemberCreateCommand;
import com.soon83.dtos.member.MemberUpdateCommand;
import com.soon83.entities.BaseEntity;
import com.soon83.entities.member.Member;
import com.soon83.utils.AssertUtil;

public class MemberFactory extends BaseEntity {
    /**
     * 회원 생성
     */
    public static Member createMember(MemberCreateCommand command) {
        validateCreateMember(command);
        return Member.builder()
                .loginId(command.getLoginId())
                .password(command.getPassword())
                .name(command.getName())
                .email(command.getEmail())
                .role(command.getRole())
                .build();
    }

    /**
     * 회원 수정
     */
    public static void updateMember(Member member, MemberUpdateCommand command) {
        validateUpdateMember(command);
        member.update(
                command.getLoginId(),
                command.getPassword(),
                command.getName(),
                command.getEmail(),
                command.getRole()
        );
    }

    private static void validateCreateMember(MemberCreateCommand command) {
        AssertUtil.notNull(command.getLoginId(), "loginId");
        AssertUtil.notNull(command.getPassword(), "password");
        AssertUtil.notNull(command.getName(), "name");
        AssertUtil.notNull(command.getEmail(), "email");
        AssertUtil.notNull(command.getRole(), "role");
    }

    private static void validateUpdateMember(MemberUpdateCommand command) {
        AssertUtil.notNull(command.getLoginId(), "loginId");
        AssertUtil.notNull(command.getPassword(), "password");
        AssertUtil.notNull(command.getName(), "name");
        AssertUtil.notNull(command.getEmail(), "email");
        AssertUtil.notNull(command.getRole(), "role");
    }
}
