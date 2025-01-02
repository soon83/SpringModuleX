package com.soon83.infrastructure.member;

import com.soon83.entities.member.MemberProjection;
import com.soon83.utils.AssertUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryJooqImpl implements MemberRepositoryJooq {
    private final DSLContext dsl;

    @Override
    public List<MemberProjection> getAllMemberProjectionList() {
        return null;
        /*return dsl
                .selectFrom(MEMBER)
                .fetchInto(MemberProjection.class);*/
    }

    public Optional<MemberProjection> getMemberProjection(Long memberId) {
        AssertUtil.notNull(memberId, "memberId");

        return Optional.empty();
        /*return dsl
                .select(MEMBER.fields())
                .from(MEMBER)
                .where(
                        eq(MEMBER.MEMBER_ID, memberId)
                )
                .fetchOptionalInto(MemberProjection.class);*/
    }
}
