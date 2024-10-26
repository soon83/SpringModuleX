package com.soon83.infrastructure.member;

import com.soon83.dtos.member.MemberSearchCondition;
import com.soon83.dtos.utils.Sortable;
import com.soon83.entities.member.Member;
import com.soon83.exceptions.member.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepository memberRepository;

    public Page<Member> getPageMemberList(MemberSearchCondition searchCondition, Pageable pageable) {
        return memberRepository.getPageMemberList(searchCondition, pageable);
    }

    public List<Member> getAllMemberList(MemberSearchCondition searchCondition, Sortable sortable) {
        return memberRepository.getAllMemberList(searchCondition, sortable);
    }

    public Member getMemberOrThrow(Long memberId) {
        return memberRepository.getMember(memberId)
                .orElseThrow(NotFoundMemberException::new);
    }

    public Member getMemberOrNull(Long memberId) {
        return memberRepository.getMember(memberId)
                .orElse(null);
    }
}
