package com.soon83.infrastructure.category;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryGroupRepositoryJooqImpl implements CategoryGroupRepositoryJooq {
    private final DSLContext dsl;
}
