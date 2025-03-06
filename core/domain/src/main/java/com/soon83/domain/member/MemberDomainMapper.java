package com.soon83.domain.member;

import com.soon83.dtos.member.MemberCreateCommand;
import com.soon83.dtos.member.MemberInfo;
import com.soon83.dtos.member.MemberUpdateCommand;
import com.soon83.entities.member.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberDomainMapper {
    public MemberInfo toMemberInfo(Member member) {
        return new MemberInfo(
                member.getMemberId(),
                member.getLoginId(),
                member.getPassword(),
                member.getName(),
                member.getEmail(),
                member.getRole()
        );
    }

    public Member toMember(MemberCreateCommand command) {
        return new Member(
                command.getLoginId(),
                command.getPassword(),
                command.getName(),
                command.getEmail(),
                command.getRole()
        );
    }

    public void updateMember(
            Member member,
            MemberUpdateCommand command
    ) {
        member.update(
                command.getLoginId(),
                command.getPassword(),
                command.getName(),
                command.getEmail(),
                command.getRole()
        );
    }
}
