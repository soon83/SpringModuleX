package com.soon83.infrastructure.member;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryJooqImpl implements MemberRepositoryJooq {
    private final DSLContext dsl;
}
