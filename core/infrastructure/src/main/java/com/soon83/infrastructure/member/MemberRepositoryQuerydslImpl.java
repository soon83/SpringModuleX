package com.soon83.infrastructure.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soon83.dtos.member.MemberSearchCondition;
import com.soon83.dtos.utils.Sortable;
import com.soon83.entities.member.Member;
import com.soon83.infrastructure.utils.CustomQuerydslRepositorySupport;
import com.soon83.utils.AssertUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.soon83.entities.member.QMember.member;
import static com.soon83.infrastructure.utils.CustomExpressionUtil.eq;
import static com.soon83.infrastructure.utils.CustomExpressionUtil.like;

public class MemberRepositoryQuerydslImpl extends CustomQuerydslRepositorySupport implements MemberRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryQuerydslImpl(JPAQueryFactory queryFactory) {
        super(Member.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Member> getPageMemberList(MemberSearchCondition searchCond, Pageable pageable) {
        return applyPagination(pageable, queryFactory -> queryFactory
                .selectFrom(member)
                .where(
                        like(member.loginId, searchCond.getLoginId()),
                        like(member.name, searchCond.getName())
                )
        );
    }

    @Override
    public List<Member> getAllMemberList(MemberSearchCondition searchCond, Sortable sortable) {
        return queryFactory
                .selectFrom(member)
                .where(
                        like(member.loginId, searchCond.getLoginId()),
                        like(member.name, searchCond.getName())
                )
                .limit(sortable.getLimit())
                .fetch();
    }

    @Override
    public Optional<Member> getMember(Long memberId) {
        AssertUtil.notNull(memberId, "memberId");

        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(eq(member.memberId, memberId))
                .fetchOne());
    }
}
