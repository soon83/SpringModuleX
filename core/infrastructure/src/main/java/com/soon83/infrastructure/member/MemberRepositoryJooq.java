package com.soon83.infrastructure.member;

import com.soon83.entities.member.MemberProjection;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryJooq {
    List<MemberProjection> getAllMemberProjectionList();

    Optional<MemberProjection> getMemberProjection(Long memberId);
}
