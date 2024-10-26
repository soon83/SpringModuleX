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
        AssertUtil.notNull(command.getLoginId(), "command.getLoginId()");
        AssertUtil.notNull(command.getPassword(), "command.getPassword()");
        AssertUtil.notNull(command.getName(), "command.getName()");
        AssertUtil.notNull(command.getEmail(), "command.getEmail()");
        AssertUtil.notNull(command.getRole(), "command.getRole()");

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
    public static Member updateMember(Member member, MemberUpdateCommand command) {
        AssertUtil.notNull(member, "member");
        AssertUtil.notNull(command.getLoginId(), "command.getLoginId()");
        AssertUtil.notNull(command.getPassword(), "command.getPassword()");
        AssertUtil.notNull(command.getName(), "command.getName()");
        AssertUtil.notNull(command.getEmail(), "command.getEmail()");
        AssertUtil.notNull(command.getRole(), "command.getRole()");

        return member.toBuilder()
                .loginId(command.getLoginId())
                .password(command.getPassword())
                .name(command.getName())
                .email(command.getEmail())
                .role(command.getRole())
                .build();
    }
}