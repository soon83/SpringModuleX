package com.soon83.infrastructure.member;

import com.soon83.dtos.member.MemberSearchCondition;
import com.soon83.dtos.utils.Sortable;
import com.soon83.entities.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryQuerydsl {
    Page<Member> getPageMemberList(MemberSearchCondition searchCond, Pageable pageable);

    List<Member> getAllMemberList(MemberSearchCondition searchCond, Sortable sortable);

    Optional<Member> getMember(Long memberId);
}
